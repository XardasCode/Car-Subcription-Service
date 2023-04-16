package com.csub.service;

import com.csub.entity.Car;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarService {
    @Transactional
    void addCar(Car car);

    @Transactional
    Car getCar(long id);

    @Transactional
    void updateCar(Car car, long id);

    @Transactional
    void deleteCar(long id);

    @Transactional
    List<Car> getAllCars();
}
