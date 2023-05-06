package com.csub.service.impl;

import com.csub.dto.ManagerDTO;
import com.csub.dto.mapper.ManagerDTOMapper;
import com.csub.entity.Manager;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.dao.ManagerDAO;
import com.csub.service.ManagerService;
import com.csub.util.PasswordManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerDAO managerDAO;

    private final ManagerDTOMapper managerDTOMapper;

    @Override
    @Transactional
    public void addManager(Manager manager) {
        log.debug("Adding manager: {}", manager);
        managerDAO.addManager(manager);
        log.debug("Manager added: {}", manager);
    }

    @Override
    @Transactional
    public ManagerDTO getManager(long id) {
        log.debug("Getting manager with id {}", id);
        Optional<Manager> manager = managerDAO.getManager(id);
        return manager.map(managerDTOMapper)
                .orElseThrow(() -> new ServerException("Manager not found", ErrorList.MANAGER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateManager(Manager manager) {
        log.debug("Updating manager: {}", manager);
        managerDAO.getManager(manager.getId());
        managerDAO.updateManager(manager);
        log.debug("Manager updated: {}", manager);
    }

    @Override
    @Transactional
    public void deleteManager(long id) {
        log.debug("Deleting manager with id {}", id);
        managerDAO.getManager(id);
        managerDAO.deleteManager(id);
        log.debug("Manager deleted: {}", id);
    }

    @Override
    @Transactional
    public List<ManagerDTO> getAllManagers() {
        log.debug("Getting all managers");
        List<Manager> managers = managerDAO.getAllManagers();
        return managers.stream()
                .map(managerDTOMapper)
                .toList();
    }

    @Override
    @Transactional
    public ManagerDTO checkManagerCredentials(String email, String password) {
        log.debug("Checking manager credentials");
        Optional<Manager> manager = managerDAO.getManagerByEmail(email);
        Manager thisManager = manager.orElseThrow(() -> new ServerException("Manager not found", ErrorList.MANAGER_NOT_FOUND));
        if (PasswordManager.checkPassword(password, thisManager.getPassword())) {
            return manager.map(managerDTOMapper)
                    .orElseThrow(() -> new ServerException("Manager not found", ErrorList.MANAGER_NOT_FOUND));
        }
        throw new ServerException("Wrong password", ErrorList.MANAGER_NOT_AUTHORIZED);
    }
}
