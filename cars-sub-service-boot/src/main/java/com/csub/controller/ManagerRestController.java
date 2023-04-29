package com.csub.controller;

import com.csub.dto.ManagerDTO;
import com.csub.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/managers")
@CrossOrigin
public class ManagerRestController {

    private final ManagerService managerService;

    @GetMapping
    public List<ManagerDTO> getAllManagers() {
        log.info("Getting all managers");
        return managerService.getAllManagers();
    }

    @GetMapping(value = "/{id}")
    public void getManager(@PathVariable long id) {
        log.info("Getting manager with id {}", id);
        managerService.getManager(id);
    }
}
