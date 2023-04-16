package com.csub.service;

import com.csub.entity.Manager;

public interface ManagerService {
    void addManager(Manager manager);

    Manager getManager(long id);

    void updateManager(Manager manager);

    void deleteManager(long id);
}
