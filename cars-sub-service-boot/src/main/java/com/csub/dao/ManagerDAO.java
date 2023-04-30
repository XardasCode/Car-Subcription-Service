package com.csub.dao;

import com.csub.entity.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerDAO {
    void addManager(Manager manager);

    void deleteManager(long id);

    void updateManager(Manager manager);

    Optional<Manager> getManager(long id);

    List<Manager> getAllManagers();

    Optional<Manager> getManagerByEmail(String email);
}
