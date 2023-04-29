package com.csub.dao;

import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.util.CarSearchInfo;

import java.util.Optional;
import java.util.List;

public interface CarDAO {
    CarStatus getCarStatusById(long id);

    void addCar(Car car);

    void deleteCar(long id);

    void updateCar(Car car);

    Optional<Car> getCar(long id);

    List<Car> getCars(CarSearchInfo info);
}
