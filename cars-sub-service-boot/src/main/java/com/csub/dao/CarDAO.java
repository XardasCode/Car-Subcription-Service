package com.csub.dao;

import com.csub.entity.Car;
import java.util.Optional;
import java.util.List;

public interface CarDAO {
    void addCar(Car car);

    void deleteCar(long id);

    void updateCar(Car car);

    Optional<Car> getCar(long id);

    List<Car> getAllCars();
}
