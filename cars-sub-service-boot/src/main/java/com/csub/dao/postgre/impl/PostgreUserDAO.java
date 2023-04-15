package com.csub.dao.postgre.impl;

import com.csub.entity.User;
import com.csub.dao.UserDAO;
import com.csub.util.UserSearchInfo;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreUserDAO implements UserDAO {

    private final EntityManager sessionFactory;

    @Override
    public List<User> getUsers(UserSearchInfo info) {
        log.debug("Getting all users with search info: {}", info);
        int page = info.getPage();
        int size = info.getSize();
        int offset = (page - 1) * size;

        return sessionFactory.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public long addUser(User user) {
        log.debug("Adding user: {}", user);
        sessionFactory.persist(user);
        log.debug("User created with id {}", user.getId());
        return user.getId();
    }

    @Override
    public void deleteUser(long id) {
        log.debug("Delete user with id: {}", id);
        User user = sessionFactory.find(User.class, id);
        if (user != null) sessionFactory.remove(user);
        log.debug("User deleted with id {}", id);
    }

    @Override
    public void updateUser(User user) {
        log.debug("Updating user: {}", user);
        sessionFactory.merge(user);
        log.debug("User updated: {}", user);
    }

    @Override
    public Optional<User> getUser(long id) {
        log.debug("Getting user with id {}", id);
        return Optional.ofNullable(sessionFactory.find(User.class, id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.debug("Getting user with email {}", email);
        return Optional.ofNullable(sessionFactory.find(User.class, email));

    }

//    @Override
//    public List<User> findUsers(String query) {
//        log.debug("Searching user");
//        return sessionFactory.createQuery("SELECT u FROM User u WHERE u.name LIKE :query OR u.email LIKE :query", User.class)
//                .setParameter("query", "%" + query + "%")
//                .getResultList();
//    }
}
