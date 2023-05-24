package com.csub.service;

import com.csub.controller.request.UserRequestDTO;
import com.csub.dto.UserDTO;
import com.csub.util.UserSearchInfo;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers(UserSearchInfo info);

    long addUser(UserRequestDTO user);

    UserDTO getUser(long id);

    UserDTO checkUserCredentials(String email, String password);

    void updateUser(UserRequestDTO user, long id);

    void deleteUser(long id);

    List<UserDTO> findUsers(String partOfName, String partOfSurname, boolean isSortByName, String sortType, UserSearchInfo info);

    void blockUser(long id);

    void unblockUser(long id);

    void generateVerificationCode(long id);

    void verifyEmail(long id, String code);

    @Transactional
    List<UserDTO> searchUsers(UserSearchInfo info);

    @Transactional
    int getPageCount(int size, List<String> filter);
}
