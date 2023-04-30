package com.csub.service;

import com.csub.dto.ManagerDTO;
import com.csub.entity.Manager;

import java.util.List;

public interface ManagerService {
    void addManager(Manager manager);

    ManagerDTO getManager(long id);

    void updateManager(Manager manager);

    void deleteManager(long id);

    List<ManagerDTO> getAllManagers();

    ManagerDTO checkManagerCredentials(String email, String password);
}
