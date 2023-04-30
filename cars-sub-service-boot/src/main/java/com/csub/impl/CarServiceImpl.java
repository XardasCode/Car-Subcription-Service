package com.csub.impl;

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
    public void addCar(CarRequestDTO car) {
        log.debug("Adding car: {}", car);
        Car carEntity = Car.createCarFromRequest(car);

        CarStatus carStatus = getCarStatusById(car.getStatusId());
        carEntity.setCarStatus(carStatus);
        carEntity.getCarStatus().getCars().add(carEntity);
        carDAO.addCar(carEntity);
        log.debug("Car added: {}", car);
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

        updateCarStatus(car, carEntity, dbCar);

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
    public void uploadImage(MultipartFile file, long carId) {
        log.debug("Uploading image");
        getCar(carId); // check if car exists, if not - throw exception
        String imagePath = imageService.storeImage(file);
        carDAO.updateImage(imagePath, carId);
        log.debug("Image uploaded");
    }

    @Override
    @Transactional
    public byte[] getImage(long carId) {
        log.debug("Getting image");
        String imagePath = carDAO.getImagePath(carId);
        log.debug("Image path: {}", imagePath);
        return imageService.getImage(imagePath);
    }

    private CarStatus getCarStatusById(String statusId) {
        log.debug("Getting car status by id {}", statusId);
        return carDAO.getCarStatusById(statusId)
                .orElseThrow(() -> new ServerException("Car status not found", ErrorList.CAR_STATUS_NOT_FOUND));
    }

    private Car getCarEntity(long id) {
        log.debug("Getting car with id {}", id);
        return carDAO.getCar(id)
                .orElseThrow(() -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));
    }

    private void updateCarStatus(CarRequestDTO car, Car carEntity, Car dbCar) {
        if (car.getStatusId() != null) {
            CarStatus carStatus = getCarStatusById(car.getStatusId());
            carEntity.setCarStatus(carStatus);
            if (!carEntity.getCarStatus().getCars().contains(carEntity)) {
                carEntity.getCarStatus().getCars().add(carEntity);
            }
        } else {
            carEntity.setCarStatus(dbCar.getCarStatus());
        }
    }
}
