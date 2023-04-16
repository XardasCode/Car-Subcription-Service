package com.csub.impl;

import com.csub.dao.CarDAO;
import com.csub.entity.Car;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarDAO carDAO;

    @Override
    @Transactional
    public void addCar(Car car) {
        log.debug("Adding car: {}", car);
        carDAO.addCar(car);
        log.debug("Car added: {}", car);
    }

    @Override
    @Transactional
    public Car getCar(long id) {
        log.debug("Getting car with id {}", id);
        Optional<Car> car = carDAO.getCar(id);
        if (car.isEmpty()) {
            log.debug("Car with id {} not found", id);
            throw new ServerException("Car not found", ErrorList.CAR_NOT_FOUND);
        }
        log.debug("Car found: {}", car);
        return car.get();
    }

    @Override
    @Transactional
    public void updateCar(Car car, long id) {
        log.debug("Updating car: {}", car);
        getCar(id);
        carDAO.updateCar(car);
        log.debug("Car updated: {}", car);
    }

    @Override
    @Transactional
    public void deleteCar(long id) {
        log.debug("Deleting subscription with id {}", id);
        getCar(id);
        carDAO.deleteCar(id);
        log.debug("Car with id {} deleted", id);
    }

    @Override
    @Transactional
    public List<Car> getAllCars() {
        log.debug("Getting all cars");
        List<Car> cars = carDAO.getAllCars();
        log.debug("Cars found: {}", cars.size());
        return cars;
    }
}
