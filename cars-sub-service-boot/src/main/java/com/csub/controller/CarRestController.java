package com.csub.controller;

import com.csub.controller.request.CarRequestDTO;
import com.csub.controller.util.JSONInfo;
import com.csub.dto.CarDTO;
import com.csub.service.CarService;
import com.csub.util.CarSearchInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@CrossOrigin
public class CarRestController {

    private final CarService carService;
    private static final String SUCCESS = "success";

    @GetMapping
    public List<CarDTO> getCars(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "") List<String> filter
    ) {
        log.info("Getting all cars");
        return carService.getCars(
                CarSearchInfo.builder()
                        .page(page)
                        .size(size)
                        .sortField(sortField)
                        .direction(direction)
                        .filter(filter)
                        .build()
        );
    }

    @GetMapping(value = "/{id}")
    public CarDTO getCar(@PathVariable long id) {
        log.info("Getting car with id {}", id);
        return carService.getCar(id);
    }
    @PostMapping
    public ResponseEntity<JSONInfo> addCar(@RequestBody @Valid CarRequestDTO car) {
        log.info("Adding car: {}", car);
        carService.addCar(car);
        log.info("Car added");
        return new ResponseEntity<>(new JSONInfo(SUCCESS,"Car added"), HttpStatus.CREATED);
    }
    @PostMapping(value = "/{id}")
    public ResponseEntity<JSONInfo> updateCar(@RequestBody CarRequestDTO car, @PathVariable long id) {
        log.info("Updating car: {}", car);
        carService.updateCar(car,id);
        log.info("Car updated");
        return new ResponseEntity<>(new JSONInfo(SUCCESS,"Car updated"), HttpStatus.OK);
    }
}
