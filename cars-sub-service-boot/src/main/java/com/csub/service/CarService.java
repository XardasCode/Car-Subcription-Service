package com.csub.service;

import com.csub.controller.request.CarRequestDTO;
import com.csub.dto.CarDTO;
import com.csub.util.CarSearchInfo;

import java.util.List;

public interface CarService {
    void addCar(CarRequestDTO car);

    CarDTO getCar(long id);

    void updateCar(CarRequestDTO car, long id);

    void deleteCar(long id);

    List<CarDTO> getCars(CarSearchInfo info);
}
