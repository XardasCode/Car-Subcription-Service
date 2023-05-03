package com.csub.controller;

import com.csub.controller.request.CarRequestDTO;
import com.csub.controller.util.JSONInfo;
import com.csub.dto.CarDTO;
import com.csub.service.CarService;
import com.csub.util.CarSearchInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@CrossOrigin
public class CarRestController {

    private final CarService carService;

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
    public ResponseEntity<JSONInfo> addCar(@RequestBody @Valid CarRequestDTO carDTO) {
        log.info("Adding car");
        carService.addCar(carDTO);
        return ResponseEntity.ok(JSONInfo.builder().message("Car added successfully").build());
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<JSONInfo> updateCar(@RequestBody CarRequestDTO carDTO, @PathVariable long id) {
        log.info("Updating car");
        carService.updateCar(carDTO, id);
        return ResponseEntity.ok(JSONInfo.builder().message("Car updated successfully").build());
    }

    @PostMapping("/image/{carId}")
    public ResponseEntity<JSONInfo> uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable long carId) {
        log.info("Uploading image");
        carService.uploadImage(file, carId);
        return ResponseEntity.ok(JSONInfo.builder().message("Image uploaded successfully").build());
    }

    @GetMapping(value = "/image/{carId}")
    public ResponseEntity<JSONInfo> getImage(@PathVariable long carId) {
        log.info("Getting car image for car with id {}", carId);
        return ResponseEntity.ok(JSONInfo.builder().message(carService.getImage(carId)).build());
    }

    @GetMapping("/page-count")
    public int getPageCount(
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") List<String> filter
    ) {
        log.info("Getting page count");
        return carService.getPageCount(size, filter);
    }
}
