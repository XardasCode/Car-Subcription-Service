package com.csub.dao.postgre.impl;

import com.csub.entity.User;
import com.csub.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreUserDAO implements UserDAO {

    private final SessionFactory sessionFactory;

    @Override
    public List<User> getAllUsers() {
        log.debug("Getting all users");
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    @Override
    public long addUser(User user) {
        log.debug("Adding user: {}", user);
        sessionFactory.getCurrentSession().persist(user);
        log.debug("User created with id {}", user.getId());
        return user.getId();
    }

    @Override
    public void deleteUser(long id) {
        log.debug("Delete user with id: {}", id);
        User user = sessionFactory.getCurrentSession().get(User.class, id);
        if (user != null) sessionFactory.getCurrentSession().remove(user);
        log.debug("User deleted with id {}", id);
    }

    @Override
    public void updateUser(User user) {
        log.debug("Updating user: {}", user);
        sessionFactory.getCurrentSession().merge(user);
        log.debug("User updated: {}", user);
    }

    @Override
    public Optional<User> getUser(long id) {
        log.debug("Getting user with id {}", id);
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(User.class, id));
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        log.debug("Getting user with email {} and password {}", email, password);
        return sessionFactory.getCurrentSession()
                .createQuery("from User where email = :email and password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResultOptional();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.debug("Getting user with email {}", email);
        return sessionFactory.getCurrentSession()
                .createQuery("from User where email = :email", User.class)
                .setParameter("email", email)
                .uniqueResultOptional();
    }
    @Override
    public List<User> findUsers(String query) {
        log.debug("Searching user");
        return sessionFactory.getCurrentSession().createQuery(query, User.class).list();
    }
}
