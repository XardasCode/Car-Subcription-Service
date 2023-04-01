package com.csub.repository.dao;

import com.csub.entity.Car;

public interface CarDAO {
    void addCar(Car car);

    void deleteCar(int id);

    void updateCar(Car car);

    Car getCar(int id);
}
