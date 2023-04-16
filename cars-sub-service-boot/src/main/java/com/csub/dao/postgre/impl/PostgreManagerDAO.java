package com.csub.dao.postgre.impl;

import com.csub.entity.Manager;
import com.csub.dao.ManagerDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreManagerDAO implements ManagerDAO {

    private final SessionFactory sessionFactory;

    @Override
    public void addManager(Manager manager) {
        log.debug("Adding manager: {}", manager);
        sessionFactory.getCurrentSession().persist(manager);
    }

    @Override
    public void deleteManager(long id) {
        log.debug("Delete manager with id: {}", id);
        Manager manager = sessionFactory.getCurrentSession().get(Manager.class, id);
        if (manager != null) sessionFactory.getCurrentSession().remove(manager);
        log.debug("Manager deleted with id {}", id);
    }

    @Override
    public void updateManager(Manager manager) {
        log.debug("Updating manager: {}", manager);
        sessionFactory.getCurrentSession().merge(manager);
        log.debug("Manager updated: {}", manager);
    }

    @Override
    public Optional<Manager> getManager(long id) {
        log.debug("Getting manager with id {}", id);
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Manager.class, id));
    }
}
