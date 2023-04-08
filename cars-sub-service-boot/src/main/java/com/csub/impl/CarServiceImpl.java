package com.csub.impl;

import com.csub.dao.CarDAO;
import com.csub.service.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarDAO carDAO;
}
