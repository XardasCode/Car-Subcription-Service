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
import java.util.OptionalLong;

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
    public OptionalLong addUser(User user) {
        log.debug("Adding user: {}", user);
        sessionFactory.persist(user);
        log.debug("User created with id {}", user.getId());
        return OptionalLong.of(user.getId());
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
        return sessionFactory
                .createQuery("from User where email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<User> findUsers(String partOfName, String partOfSurname, boolean isSortByName, String sortType) {

        log.debug("Searching user");
        if (partOfName == null) partOfName = "";

        if (partOfSurname == null) partOfSurname = "";

        if (sortType == null) sortType = "";

        String orderBy = "";
        if (isSortByName) {
            if (sortType.equals("DESC")) {
                orderBy = "order by name DESC";
            } else if (sortType.equals("ASC")) {
                orderBy = "order by name ASC";
            } else {
                orderBy = "order by name";
            }
        }

        return sessionFactory
                .createQuery("from User where name like :name and surname like :surname " + orderBy, User.class)
                .setParameter("name", "%" + partOfName + "%")
                .setParameter("surname", "%" + partOfSurname + "%")
                .getResultList();
    }
}
