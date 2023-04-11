package com.csub.impl;

import com.csub.entity.Manager;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.dao.ManagerDAO;
import com.csub.service.ManagerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerDAO managerDAO;

    @Override
    @Transactional
    public void addManager(Manager manager) {
        log.debug("Adding manager: {}", manager);
        managerDAO.addManager(manager);
        log.debug("Manager added: {}", manager);
    }
    @Override
    public Manager getManager(long id) {
        log.debug("Getting manager with id {}", id);
        Optional<Manager> manager = managerDAO.getManager(id);
        if (manager.isEmpty()) {
            log.debug("Manager with id {} not found", id);
            throw new ServerException("Manager with id " + id + " not found", ErrorList.MANAGER_NOT_FOUND);
        }
        log.debug("Manager found: {}", manager);
        return manager.get();
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
}
