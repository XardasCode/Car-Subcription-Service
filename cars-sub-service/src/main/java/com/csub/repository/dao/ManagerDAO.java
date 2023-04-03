package com.csub.repository.dao;

import com.csub.entity.Manager;

import java.util.Optional;

public interface ManagerDAO {
    void addManager(Manager manager);

    void deleteManager(long id);

    void updateManager(Manager manager);

    Optional<Manager> getManager(long id);
}
