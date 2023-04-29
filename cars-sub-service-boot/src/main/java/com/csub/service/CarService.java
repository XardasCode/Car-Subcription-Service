package com.csub.service;

import com.csub.dto.CarDTO;
import com.csub.entity.Car;
import com.csub.util.CarSearchInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    void addCar(Car car);

    CarDTO getCar(long id);

    void updateCar(Car car, long id);

    void deleteCar(long id);

    List<CarDTO> getCars(CarSearchInfo info);

    void uploadImage(MultipartFile file, long carId);

    byte[] getImage(long carId);
}
