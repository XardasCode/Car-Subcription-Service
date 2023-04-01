package com.csub.repository.dao;

import com.csub.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> getAllUsers();

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    Optional<User> getUser(long id);
}
