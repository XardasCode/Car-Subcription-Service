package com.csub.controller;

import com.csub.dto.CarDTO;
import com.csub.service.CarService;
import com.csub.util.CarSearchInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
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
}
