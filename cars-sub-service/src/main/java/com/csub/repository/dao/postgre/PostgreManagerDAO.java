package com.csub.repository.dao.postgre;

import com.csub.entity.Manager;
import com.csub.repository.dao.ManagerDAO;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgreManagerDAO implements ManagerDAO {

    SessionFactory sessionFactory;

    @Autowired
    public PostgreManagerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void addManager(Manager manager) {
        sessionFactory.getCurrentSession().persist(manager);
    }

    @Override
    @Transactional
    public void deleteManager(long id) {
        Manager manager = sessionFactory.getCurrentSession().get(Manager.class, id);
        if (manager != null) sessionFactory.getCurrentSession().remove(manager);
    }

    @Override
    @Transactional
    public void updateManager(Manager manager) {
        sessionFactory.getCurrentSession().merge(manager);
    }

    @Override
    @Transactional
    public Optional<Manager> getManager(long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Manager.class, id));
    }
}
