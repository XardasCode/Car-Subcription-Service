package com.csub.service;

import com.csub.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void addUser(User user);

    User getUser(long id);

    void updateUser(User user);

    void deleteUser(long id);
}
