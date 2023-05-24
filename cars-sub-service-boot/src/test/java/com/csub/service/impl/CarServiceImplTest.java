package com.csub.service.impl;


import com.csub.controller.request.CarRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dto.CarDTO;
import com.csub.dto.mapper.CarDTOMapper;
import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.entity.Subscription;
import com.csub.util.CarSearchInfo;
import com.csub.util.CarStatusList;
import com.csub.util.ImageService;
import com.uploadcare.api.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarDAO carDAO;
    private final CarDTOMapper carDTOMapper = new CarDTOMapper();
    Car car;
    CarStatus carStatus;

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(carDAO, carDTOMapper, new ImageService(new Client("", "")));
        carStatus = CarStatus.builder().id(1).name("AVAILABLE").cars(new HashSet<>()).build();

        Subscription subscription = Subscription.builder().build();
        car = Car.builder()
                .id(0)
                .name("Camry")
                .model("3.5 6AT Premium")
                .brand("Toyota ")
                .year(2019)
                .color("Black")
                .price(2250)
                .fuelType("Gasoline")
                .chassisNumber("9591")
                .regNumber("TT")
                .regDate("2022-10-05")
                .mileage(11230)
                .lastServiceDate("2022-10-05")
//                .createDate("2022-10-05")
//                .lastUpdateDate("2022-10-05")
                .carStatus(carStatus)
                .subscription(subscription)
                .build();
    }

    @DisplayName("addCar checks if the carDao method is called")
    @Test
    void addCar() {
        CarRequestDTO carRequestDTO = CarRequestDTO.builder()
                .name(car.getName())
                .model(car.getModel())
                .brand(car.getBrand())
                .year(String.valueOf(car.getYear()))
                .color(car.getColor())
                .price(String.valueOf(car.getPrice()))
                .fuelType(car.getFuelType())
                .chassisNumber(car.getChassisNumber())
                .regNumber(car.getRegNumber())
                .regDate(car.getRegDate())
                .mileage(String.valueOf(car.getMileage()))
                .lastServiceDate(car.getLastServiceDate())
                .build();
        Mockito.when(carDAO.getCarStatusById(CarStatusList.AVAILABLE.getStatusId())).thenReturn(Optional.ofNullable(carStatus));
        Mockito.when(carDAO.addCar(car)).thenReturn(OptionalLong.of(car.getId()));
        long actual = carService.addCar(carRequestDTO);
        Mockito.verify(carDAO, Mockito.times(1)).addCar(any());
        assertEquals(0, actual);
    }

    @DisplayName("getCar must return valid car when id is valid")
    @Test
    void getCar() {
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        CarDTO actual = carService.getCar(car.getId());
        Mockito.verify(carDAO, Mockito.times(1)).getCar(car.getId());
        assertAll(() -> {
            assertEquals(car.getId(), actual.id());
            assertEquals(car.getName(), actual.name());
        });
    }
    @DisplayName("getCar must return valid list of cars")
    @Test
    void getCars() {
        CarSearchInfo info = CarSearchInfo.builder()
                .filter(new ArrayList<>())
                .sortField("brand")
                .direction("ASC")
                .page(1)
                .size(12)
                .build();
        List <Car> cars = new ArrayList<>();
        cars.add(car);
        Mockito.when(carDAO.getCars(info)).thenReturn(cars);
        List<CarDTO> actual = carService.getCars(info);
        Mockito.verify(carDAO, Mockito.times(1)).getCars(info);
        assertAll(() -> {
            assertEquals(car.getId(), actual.get(0).id());
            assertEquals(car.getName(), actual.get(0).name());
        });
    }

    @DisplayName("getCar must throw ServerException when id is not valid")
    @Test
    void getCarMustThrowServerException() {
        Mockito.when(carDAO.getCar(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carService.getCar(1L))
                .hasMessage("Car not found");
        Mockito.verify(carDAO, Mockito.times(1)).getCar(1L);
    }

    @DisplayName("updateCar checks if the carDao method is called")
    @Test
    void updateCar() {
        Mockito.doNothing().when(carDAO).updateCar(any());
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        carService.updateCar(new CarRequestDTO(), car.getId());
        Mockito.verify(carDAO, Mockito.times(1)).updateCar(car);
    }

    @DisplayName("deleteCar checks if the carDao method is called")
    @Test
    void deleteCar() {
        Mockito.doNothing().when(carDAO).deleteCar(anyLong());
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCar(car.getId());
        Mockito.verify(carDAO, Mockito.times(1)).deleteCar(car.getId());
    }

    @DisplayName("getImage checks if the carDao method is called")
    @Test
    void getImage() {

        Mockito.when(carDAO.getImageURL(car.getId())).thenReturn("imagePath");
        String actual = carService.getImage(car.getId());
        Mockito.verify(carDAO, Mockito.times(1)).getImageURL(car.getId());
        assertEquals("imagePath", actual);
    }
    @DisplayName("getPageCount checks if the carDao method getCarsCount is called")
    @Test
    void  getPageCount() {
        int size = 12;
        List<String> filter = new ArrayList<>();
        filter.add("brand:Audi");
        Mockito.when(carDAO.getCarsCount(size,filter)).thenReturn(24);
        int actual = carService.getPageCount(size,filter);
        Mockito.verify(carDAO, Mockito.times(1)).getCarsCount(size,filter);
        assertEquals(2, actual);
    }
}