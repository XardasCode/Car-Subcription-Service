package com.csub.impl;


import com.csub.dao.CarDAO;
import com.csub.dto.CarDTO;
import com.csub.dto.mapper.CarDTOMapper;
import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.entity.Subscription;
import org.junit.jupiter.api.BeforeEach;
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
public class CarServiceImplTest {
    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarDAO carDAO;
    private  CarDTOMapper carDTOMapper = new CarDTOMapper();
    Car car;
    CarStatus carStatus = new CarStatus();
    @BeforeEach
    void setUp()
    {
        carService = new CarServiceImpl(carDAO,carDTOMapper);
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
                .mileage("11230")
                .lastServiceDate("05.10.2022")
                .statusId("1")
                .carStatus(carStatus)
                .subscriptions(subscription)
                .build();
    }
    @Test
    void addCar() {
        Mockito.doNothing().when(carDAO).addCar(ArgumentMatchers.any());
        carService.addCar(car);
        Mockito.verify(carDAO, Mockito.times(1)).addCar(car);
    }

    @Test
    void getCar() {
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        CarDTO actual = carService.getCar(car.getId());
        Mockito.verify(carDAO, Mockito.times(1)).getCar(car.getId());
        assertAll(()->{
            assertEquals(car.getId(),actual.id());
            assertEquals(car.getName(),actual.name());
        });
    }
    @Test
    void getCarMustThrowServerException() {
        Mockito.when(carDAO.getCar(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carService.getCar(1))
                .hasMessage("Car not found");
        Mockito.verify(carDAO, Mockito.times(1)).getCar(car.getId());
    }
    @Test
    void updateCar() {
        Mockito.doNothing().when(carDAO).updateCar(any());
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        carService.updateCar(car,1);
        Mockito.verify(carDAO, Mockito.times(1)).updateCar(car);
    }
    @Test
    void deleteCar() {
        Mockito.doNothing().when(carDAO).deleteCar(anyLong());
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCar(1);
        Mockito.verify(carDAO, Mockito.times(1)).deleteCar(1);
    }
}