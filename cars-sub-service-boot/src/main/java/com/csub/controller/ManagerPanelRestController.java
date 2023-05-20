package com.csub.controller;

import com.csub.service.ManagerPanelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/v1/manager-panel")
@RequiredArgsConstructor
public class ManagerPanelRestController {

    private final ManagerPanelService managerPanelService;



}
