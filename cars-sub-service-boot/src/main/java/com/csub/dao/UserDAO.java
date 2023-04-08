package com.csub.dao;

import com.csub.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> getAllUsers();

    long addUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    Optional<User> getUser(long id);

    Optional<User> getUserByEmailAndPassword(String email, String password);

    Optional<User> getUserByEmail(String email);
}
