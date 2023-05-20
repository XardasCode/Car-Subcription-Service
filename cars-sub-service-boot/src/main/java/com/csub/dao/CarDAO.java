package com.csub.dao;

import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.util.CarSearchInfo;

import java.util.Optional;
import java.util.List;
import java.util.OptionalLong;

public interface CarDAO {
    OptionalLong addCar(Car car);

    void deleteCar(long id);

    void updateCar(Car car);

    Optional<Car> getCar(long id);

    List<Car> getCars(CarSearchInfo info);

    void updateImage(String imagePath, long carId);

    String getImageURL(long carId);

    Optional<CarStatus> getCarStatusById(String statusId);

    Optional<CarStatus> getCarStatusById(int statusId);

    int getCarsCount(int size, List<String> filter);
}
