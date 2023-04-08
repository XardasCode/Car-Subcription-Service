package com.csub.service.impl;

import com.csub.entity.User;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.repository.dao.UserDAO;
import com.csub.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        log.debug("Getting all users");
        List<User> users = userDAO.getAllUsers();
        log.debug("Users found: {}", users.size());
        return users;
    }

    @Override
    @Transactional
    public long addUser(User user) {
        log.debug("Adding user: {}", user);
        checkIfEmailAlreadyExists(user.getEmail());
        long id = userDAO.addUser(user);
        if (id == 0) {
            log.warn("User not added");
            throw new ServerException("User not added", ErrorList.USER_NOT_CREATED);
        }
        log.debug("User added: {}", user);
        return id;
    }

    @Override
    @Transactional
    public User getUser(long id) {
        log.debug("Getting user with id {}", id);
        Optional<User> user = userDAO.getUser(id);
        if (user.isEmpty()) {
            log.warn("User with id {} not found", id);
            throw new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND);
        }
        log.debug("User found: {}", user);
        return user.get();
    }

    @Override
    @Transactional
    public User checkUserCredentials(String email, String password) {
        log.debug("Checking user credentials");
        Optional<User> user = userDAO.getUserByEmailAndPassword(email, password);
        if (user.isEmpty()) {
            log.warn("User with email {} and password {} not found", email, password);
            throw new ServerException("User with email " + email + " or password " + password + " not found", ErrorList.USER_NOT_FOUND);
        }
        log.debug("User found: {}", user);
        return user.get();
    }

    @Override
    @Transactional
    public void updateUser(User user, long id) {
        log.debug("Updating user: {}", user);
        Optional<User> optionalUser = userDAO.getUser(id);
        User checkUser = optionalUser.orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
        if (!checkUser.getEmail().equals(user.getEmail())) {
            log.debug("Email changed, checking if email already exists");
            checkIfEmailAlreadyExists(user.getEmail());
        }
        userDAO.updateUser(user);
        log.debug("User updated: {}", user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        log.debug("Deleting user with id {}", id);
        userDAO.deleteUser(id);
        log.debug("User with id {} deleted", id);
    }


    private void checkIfEmailAlreadyExists(String email) {
        log.debug("Checking if user with email {} already exists", email);
        Optional<User> user = userDAO.getUserByEmail(email);
        if (user.isPresent()) {
            log.warn("User with email {} already exists", email);
            throw new ServerException("User with email " + email + " already exists", ErrorList.USER_ALREADY_EXISTS);
        }
        log.debug("User with email {} does not exist", email);
    }
}
