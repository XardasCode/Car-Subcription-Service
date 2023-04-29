package com.csub.impl;


import com.csub.controller.request.CarRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dto.CarDTO;
import com.csub.dto.mapper.CarDTOMapper;
import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.entity.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;
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
    CarRequestDTO carRequestDTO;
    CarStatus carStatus = new CarStatus();
    @BeforeEach
    void setUp()
    {
        carService = new CarServiceImpl(carDAO,carDTOMapper);
        carStatus.setId(1);
        carStatus.setName("In Stock");

        Subscription subscription = Subscription.builder().build();
        car = Car.builder()
                .name("Camry")
                .model("3.5 6AT Premium")
                .brand("Toyota ")
                .year(2019)
                .color("Black")
                .price(2250)
                .fuelType("Gasoline")
                .chassisNumber("9591")
                .regNumber("TT")
                .regDate("25.05.2020")
                .mileage(11230)
                .lastServiceDate("05.10.2022")
                .statusId(1)
                .build();
        carRequestDTO = CarRequestDTO.builder()
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
                .statusId(String.valueOf(car.getStatusId()))
                .build();
    }
    @DisplayName("addCar checks if the carDao method is called")
    @Test
    void addCar() {
        Mockito.doNothing().when(carDAO).addCar(car);
        carService.addCar(carRequestDTO);
        Mockito.verify(carDAO, Mockito.times(1)).addCar(car);
    }
    @DisplayName("getCar must return valid car when id is valid")
    @Test
    void getCar() {
        car.setId(1);
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        CarDTO actual = carService.getCar(car.getId());
        Mockito.verify(carDAO, Mockito.times(1)).getCar(car.getId());
        assertAll(()->{
            assertEquals(car.getId(),actual.id());
            assertEquals(car.getName(),actual.name());
        });
    }
    @DisplayName("getCar must throw ServerException when id is not valid")
    @Test
    void getCarMustThrowServerException() {
        car.setId(1);
        Mockito.when(carDAO.getCar(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carService.getCar(1))
                .hasMessage("Car not found");
        Mockito.verify(carDAO, Mockito.times(1)).getCar(car.getId());
    }
    @DisplayName("updateCar checks if the carDao method is called")
    @Test
    void updateCar() {
        Mockito.doNothing().when(carDAO).updateCar(any());
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        carService.updateCar(carRequestDTO,car.getId());
        Mockito.verify(carDAO, Mockito.times(1)).updateCar(car);
    }
    @DisplayName("deleteCar checks if the carDao method is called")
    @Test
    void deleteCar() {
        car.setId(1);
        Mockito.doNothing().when(carDAO).deleteCar(anyLong());
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCar(1);
        Mockito.verify(carDAO, Mockito.times(1)).deleteCar(1);
    }
}