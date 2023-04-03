package com.csub.repository.dao.postgre;

import com.csub.entity.User;
import com.csub.repository.dao.UserDAO;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostgreUserDAO implements UserDAO {

    SessionFactory sessionFactory;

    @Autowired
    public PostgreUserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional
    public List<User> getAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = sessionFactory.getCurrentSession().get(User.class, id);
        if (user != null) sessionFactory.getCurrentSession().remove(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    @Transactional
    public Optional<User> getUser(long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(User.class, id));
    }
}
