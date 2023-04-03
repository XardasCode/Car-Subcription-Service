package com.csub.service.impl;

import com.csub.entity.Manager;
import com.csub.exception.ManagerAlreadyExistsException;
import com.csub.exception.ManagerNotFoundException;
import com.csub.repository.dao.ManagerDAO;
import com.csub.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {
    ManagerDAO managerDAO;

    @Autowired
    public ManagerServiceImpl(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO;
    }
    @Override
    public void addManager(Manager manager) {
        long id = manager.getId();
        Optional<Manager> managerOptional = managerDAO.getManager(id);

        if (managerOptional.isPresent()) {
            log.warn("Manager with id {} already exists", id);
            throw new ManagerAlreadyExistsException("Manager with id " + id + " already exists");
        }
        managerDAO.addManager(manager);
        log.trace("Manager added: {}", manager);
    }
    @Override
    public Manager getManager(long id) {
        Optional<Manager> manager = managerDAO.getManager(id);
        if (manager.isEmpty()) {
            log.warn("Manager with id {} not found", id);
            throw new ManagerNotFoundException("Manager with id " + id + " not found");
        }
        log.trace("User found: {}", manager);
        return manager.get();
    }

    @Override
    public void updateManager(Manager manager) {
        long id = manager.getId();
        Optional<Manager> managerOptional = managerDAO.getManager(id);
        if (managerOptional.isEmpty()) {
            log.warn("Manager with id {} not found", id);
            throw new ManagerNotFoundException("Manager with id " + id + " not found");
        }
        managerDAO.updateManager(manager);
        log.trace("Manager updated: {}", manager);
    }
    @Override
    public void deleteManager(long id) {
        Optional<Manager> manager = managerDAO.getManager(id);
        if (manager.isEmpty()) {
            log.warn("Manager with id {} not found", id);
            throw new ManagerNotFoundException("Manager with id " + id + " not found");
        }
        managerDAO.deleteManager(id);
        log.trace("Manager deleted: {}", manager);
    }
}
