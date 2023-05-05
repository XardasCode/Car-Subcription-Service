package com.csub.impl;


import com.csub.controller.request.CarRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dto.CarDTO;
import com.csub.dto.mapper.CarDTOMapper;
import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.entity.Subscription;
import com.csub.util.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uploadcare.api.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

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
    CarStatus carStatus = new CarStatus();

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(carDAO, carDTOMapper, new ImageService(new Client("", "")));
        carStatus.setId(1);
        carStatus.setName("In Stock");

        Subscription subscription = Subscription.builder().build();
        car = Car.builder()
                .id(1)
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
                .carStatus(carStatus)
                .subscription(subscription)
                .build();
    }

    @DisplayName("addCar checks if the carDao method is called")
    @Test
    void addCar() {
        CarRequestDTO carRequestDTO = CarRequestDTO.builder()
                .name("Toyota Camry")
                .model("SE")
                .brand("Toyota")
                .year("2021")
                .color("Black")
                .price("25000")
                .fuelType("Gasoline")
                .chassisNumber("12345678901234567")
                .regNumber("ABC123")
                .regDate("2022-01-01")
                .mileage("10000")
                .lastServiceDate("2022-03-01")
                .statusId("1")
                .build();
        Mockito.doNothing().when(carDAO).addCar(any());
        Mockito.when(carDAO.getCarStatusById("1")).thenReturn(Optional.ofNullable(carStatus));
        carService.addCar(carRequestDTO);
        Mockito.verify(carDAO, Mockito.times(1)).addCar(any());
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

    @DisplayName("getCar must throw ServerException when id is not valid")
    @Test
    void getCarMustThrowServerException() {
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
        carService.updateCar(new CarRequestDTO(), 1);
        Mockito.verify(carDAO, Mockito.times(1)).updateCar(car);
    }

    @DisplayName("deleteCar checks if the carDao method is called")
    @Test
    void deleteCar() {
        Mockito.doNothing().when(carDAO).deleteCar(anyLong());
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCar(1);
        Mockito.verify(carDAO, Mockito.times(1)).deleteCar(1);
    }
}