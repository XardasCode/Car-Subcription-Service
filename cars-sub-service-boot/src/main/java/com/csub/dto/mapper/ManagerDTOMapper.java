package com.csub.dto.mapper;

import com.csub.dto.ManagerDTO;
import com.csub.entity.Manager;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ManagerDTOMapper implements Function<Manager, ManagerDTO> {
    @Override
    public ManagerDTO apply(Manager manager) {
        return new ManagerDTO(
                manager.getId(),
                manager.getName(),
                manager.getSurname(),
                manager.getEmail());
    }
}
