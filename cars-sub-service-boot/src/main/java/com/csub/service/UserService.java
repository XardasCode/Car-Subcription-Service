package com.csub.service;

import com.csub.entity.User;
import jakarta.transaction.Transactional;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {


    List<User> getAllUsers();

    long addUser(User user);

    User getUser(long id);

    User checkUserCredentials(String email, String password);

    void updateUser(User user, long id);

    void deleteUser(long id);

    List<User> findUsers(String partOfName, String partOfSurname, boolean isSortByName, String sortType);
}
