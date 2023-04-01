package com.csub.service.impl;

import com.csub.entity.User;
import com.csub.exception.UserNotFoundException;
import com.csub.repository.dao.UserDAO;
import com.csub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            log.warn("No users created yet");
            throw new UserNotFoundException("No users created yet. Please create a user first.");
        }
        log.trace("Users found: {}", users);
        return users;
    }

    @Override
    public void addUser(User user) {
        long id = user.getId();
        Optional<User> userOptional = userDAO.getUser(id);

        if (userOptional.isPresent()) {
            log.warn("User with id {} already exists", id);
            throw new UserNotFoundException("User with id " + id + " already exists");
        }

        userDAO.addUser(user);
        log.trace("User added: {}", user);
    }

    @Override
    public User getUser(long id) {
        Optional<User> user = userDAO.getUser(id);
        if (user.isEmpty()) {
            log.warn("User with id {} not found", id);
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        log.trace("User found: {}", user);
        return user.get();
    }

    @Override
    public void updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public void deleteUser(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }
}
