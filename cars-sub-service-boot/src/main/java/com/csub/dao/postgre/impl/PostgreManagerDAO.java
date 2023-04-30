package com.csub.dao.postgre.impl;

import com.csub.entity.Manager;
import com.csub.dao.ManagerDAO;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreManagerDAO implements ManagerDAO {

    private final EntityManager sessionFactory;

    @Override
    public void addManager(Manager manager) {
        log.debug("Adding manager: {}", manager);
        sessionFactory.persist(manager);
    }

    @Override
    public void deleteManager(long id) {
        log.debug("Delete manager with id: {}", id);
        Manager manager = sessionFactory.find(Manager.class, id);
        if (manager != null) sessionFactory.remove(manager);
        log.debug("Manager deleted with id {}", id);
    }

    @Override
    public void updateManager(Manager manager) {
        log.debug("Updating manager: {}", manager);
        sessionFactory.merge(manager);
        log.debug("Manager updated: {}", manager);
    }

    @Override
    public Optional<Manager> getManager(long id) {
        log.debug("Getting manager with id {}", id);
        return Optional.ofNullable(sessionFactory.find(Manager.class, id));
    }

    @Override
    public List<Manager> getAllManagers() {
        log.debug("Getting all managers");
        return sessionFactory.createQuery("from Manager", Manager.class).getResultList();
    }

    @Override
    public Optional<Manager> getManagerByEmail(String email) {
        log.debug("Getting manager with email {}", email);
        return sessionFactory.createQuery("from Manager where email = :email", Manager.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();
    }
}
