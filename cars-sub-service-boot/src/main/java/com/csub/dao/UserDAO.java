package com.csub.dao;

import com.csub.entity.User;
import com.csub.entity.UserRole;
import com.csub.util.UserSearchInfo;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public interface UserDAO {

    List<User> getUsers(UserSearchInfo info);

    OptionalLong addUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    Optional<User> getUser(long id);

    Optional<User> getUserByEmail(String email);

    List<User> findUsers(String partOfName, String partOfSurname, boolean isSortByName, String sortType);

    UserRole getRoleById(int roleId);

    List<User> searchUsers(UserSearchInfo info);

    int getUsersCount(int size, List<String> filter);

    List<User> getUsersWithSubscriptions();
}
