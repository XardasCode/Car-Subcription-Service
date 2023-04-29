package com.csub.impl;

import com.csub.dao.CarDAO;
import com.csub.dto.CarDTO;
import com.csub.dto.mapper.CarDTOMapper;
import com.csub.entity.Car;
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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarDAO carDAO;

    private final CarDTOMapper carDTOMapper;

    private final ImageService imageService;

    @Override
    @Transactional
    public void addCar(Car car) {
        log.debug("Adding car: {}", car);
        carDAO.addCar(car);
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
}
