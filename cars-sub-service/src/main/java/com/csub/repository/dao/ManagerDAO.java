package com.csub.repository.dao;

import com.csub.entity.Manager;

public interface ManagerDAO {
    void addManager(Manager manager);

    void deleteManager(int id);

    void updateManager(Manager manager);

    Manager getManager(int id);
}
