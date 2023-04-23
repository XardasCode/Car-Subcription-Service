package com.csub.service;

import com.csub.controller.request.UserRequestDTO;
import com.csub.dto.UserDTO;
import com.csub.entity.User;
import com.csub.util.UserSearchInfo;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers(UserSearchInfo info);

    long addUser(UserRequestDTO user);

    UserDTO getUser(long id);

    UserDTO checkUserCredentials(String email, String password);

    void updateUser(UserRequestDTO user, long id);

    void deleteUser(long id);

    List<UserDTO> findUsers(String partOfName, String partOfSurname, boolean isSortByName, String sortType, UserSearchInfo info);
}
