package com.csub.service;

import com.csub.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    long addUser(User user);

    User getUser(long id);

    User checkUserCredentials(String email, String password);

    void updateUser(User user, long id);

    void deleteUser(long id);
}
