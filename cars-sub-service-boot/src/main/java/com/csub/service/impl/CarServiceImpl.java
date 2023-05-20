package com.csub.service.impl;

import com.csub.controller.request.CarRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dto.CarDTO;
import com.csub.dto.mapper.CarDTOMapper;
import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.CarService;
import com.csub.util.CarSearchInfo;
import com.csub.util.CarStatusList;
import com.csub.util.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarDAO carDAO;

    private final CarDTOMapper carDTOMapper;

    private final ImageService imageService;

    @Override
    @Transactional
    public long addCar(CarRequestDTO car) {
        log.debug("Adding car: {}", car);
        Car carEntity = Car.createCarFromRequest(car);

        CarStatus carStatus = getCarStatusById(CarStatusList.AVAILABLE.getStatusId());
        carEntity.setCarStatus(carStatus);
        carEntity.getCarStatus().getCars().add(carEntity);
        long carId = carDAO.addCar(carEntity).orElseThrow(() -> new ServerException("Car not added", ErrorList.CAR_NOT_CREATED));
        log.debug("Car added: {}", car);
        return carId;
    }

    @Override
    @Transactional
    public CarDTO getCar(long id) {
        log.debug("Getting car with id {}", id);
        Optional<Car> car = carDAO.getCar(id);
        return car.map(carDTOMapper)
                .orElseThrow(() -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateCar(CarRequestDTO car, long id) {
        log.debug("Updating car: {}", car);

        Car dbCar = getCarEntity(id);
        Car carEntity = Car.createCarFromRequest(car);


        Car.mergeCars(dbCar, carEntity);

        carDAO.updateCar(carEntity);
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
    public List<CarDTO> getCars(CarSearchInfo info) {
        log.debug("Getting all cars");
        List<Car> cars = carDAO.getCars(info);
        log.debug("Cars found: {}", cars.size());
        return cars.stream().map(carDTOMapper).toList();
    }

    @Override
    @Transactional
    public int getPageCount(int size, List<String> filter) {
        log.debug("Getting page count");
        int count = carDAO.getCarsCount(size, filter);
        log.debug("Count: {}", count);
        return (int) Math.ceil((double) count / size);
    }

    @Override
    @Transactional
    public void uploadImage(MultipartFile file, long carId) {
        log.debug("Uploading image");
        getCar(carId); // check if car exists, if not - throw exception
        String imagePath = imageService.uploadImage(file);
        carDAO.updateImage(imagePath, carId);
        log.debug("Image uploaded");
    }

    @Override
    @Transactional
    public String getImage(long carId) {
        log.debug("Getting car image URL for car: {}", carId);
        String imagePath = carDAO.getImageURL(carId);
        log.debug("Image URL: {}", imagePath);
        return imagePath;
    }

    private CarStatus getCarStatusById(int statusId) {
        log.debug("Getting car status by id {}", statusId);
        return carDAO.getCarStatusById(statusId)
                .orElseThrow(() -> new ServerException("Car status not found", ErrorList.CAR_STATUS_NOT_FOUND));
    }

    private Car getCarEntity(long id) {
        log.debug("Getting car with id {}", id);
        return carDAO.getCar(id)
                .orElseThrow(() -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));
    }
}

