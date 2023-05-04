package com.csub.dto.mapper;

import com.csub.dto.CarDTO;
import com.csub.entity.Car;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CarDTOMapper implements Function<Car, CarDTO> {
    @Override
    public CarDTO apply(Car car) {
        return new CarDTO(
                car.getId(),
                car.getName(),
                car.getModel(),
                car.getBrand(),
                car.getYear(),
                car.getColor(),
                car.getPrice(),
                car.getFuelType(),
                car.getChassisNumber(),
                car.getRegNumber(),
                car.getRegDate(),
                car.getMileage(),
                car.getLastServiceDate(),
                car.getCarStatus().getName(),
                car.getImagePath());
    }
}
