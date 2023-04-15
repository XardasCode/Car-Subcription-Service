package com.csub.controller;

import com.csub.dto.ManagerDTO;
import com.csub.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/managers")
public class ManagerRestController {

    private final ManagerService managerService;

    @GetMapping
    public List<ManagerDTO> getAllManagers() {
        log.info("Getting all managers");
        managerService.getAllManagers();
    }

    @GetMapping(value = "/{id}")
    public void getManager(@PathVariable long id) {
        log.info("Getting manager with id {}", id);
        managerService.getManager(id);
    }
}
