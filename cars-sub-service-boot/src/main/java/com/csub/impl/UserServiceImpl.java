package com.csub.impl;

import com.csub.controller.request.UserRequestDTO;
import com.csub.dto.UserDTO;
import com.csub.dto.mapper.UserDTOMapper;
import com.csub.entity.User;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.dao.UserDAO;
import com.csub.service.UserService;
import com.csub.util.UserSearchInfo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.csub.util.PasswordManager.checkPassword;
import static com.csub.util.PasswordManager.encryptPassword;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final UserDTOMapper userDTOMapper;

    @Override
    @Transactional
    public List<UserDTO> getUsers(UserSearchInfo info) {
        log.debug("Getting all users");
        List<User> users = userDAO.getUsers(info);
        log.debug("Users found: {}", users.size());
        return users.stream().map(userDTOMapper).toList();
    }

    @Override
    @Transactional
    public long addUser(UserRequestDTO user) {
        log.debug("Adding user: {}", user);
        checkIfEmailAlreadyExists(user.getEmail());
        user.setPassword(encryptPassword(user.getPassword()));
        User userEntity = User.mapUserRequestDTOToUser(user);
        long id = userDAO.addUser(userEntity).orElseThrow(() -> new ServerException("User not added", ErrorList.USER_NOT_CREATED));
        log.debug("User added: {}", user);
        return id;
    }

    @Override
    @Transactional
    public UserDTO getUser(long id) {
        log.debug("Getting user with id {}", id);
        Optional<User> user = userDAO.getUser(id);
        return user.map(userDTOMapper).orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public UserDTO checkUserCredentials(String email, String password) {
        log.debug("Checking user credentials");
        Optional<User> user = userDAO.getUserByEmail(email);
        if (user.isEmpty()) {
            log.warn("User with email {} and password {} not found", email, password);
            throw new ServerException("User with email " + email + " or password " + password + " not found", ErrorList.USER_NOT_FOUND);
        }
        User thisUser = user.get();
        if (!checkPassword(password, thisUser.getPassword())) {
            log.warn("User with email {} has bad pass: {}", email, password);
            throw new ServerException("User with email " + email + " or password " + password + " not found", ErrorList.USER_NOT_FOUND);
        }
        log.debug("User found: {}", user);
        return user.map(userDTOMapper).orElseThrow(() -> new ServerException("User with email " + email + " not found", ErrorList.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateUser(UserRequestDTO user, long id) {
        log.debug("Updating user: {}", user);
        Optional<User> optionalUser = userDAO.getUser(id);
        User checkUser = optionalUser.orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
        User userEntity = User.mapUserRequestDTOToUser(user);
        if (!checkUser.getEmail().equals(user.getEmail())) {
            log.debug("Email changed, checking if email already exists");
            checkIfEmailAlreadyExists(userEntity.getEmail());
        }
        userDAO.updateUser(userEntity);
        log.debug("User updated: {}", userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        log.debug("Deleting user with id {}", id);
        getUser(id); // check if user exists and throw exception if not
        userDAO.deleteUser(id);
        log.debug("User with id {} deleted", id);
    }


    private void checkIfEmailAlreadyExists(String email) {
        log.debug("Checking if user with email {} already exists", email);
        userDAO.getUserByEmail(email).ifPresent(u -> {
            log.warn("User with email {} already exists", email);
            throw new ServerException("User with email " + email + " already exists", ErrorList.USER_ALREADY_EXISTS);
        });
        log.debug("User with email {} does not exist", email);
    }

    @Transactional
    @Override
    public List<UserDTO> findUsers(String partOfName, String partOfSurname, boolean isSortByName, String sortType, UserSearchInfo info) { // sortType - ASC / DESC
        if ((partOfName == null) && (partOfSurname == null) && (!isSortByName) && (sortType == null)) {
            return userDAO.getUsers(info).stream().map(userDTOMapper).toList();
        }

        List<User> users = userDAO.findUsers(partOfName, partOfSurname, isSortByName, sortType);
        if (users.isEmpty()) {
            log.warn("User not found");
            throw new ServerException("User not found", ErrorList.USER_NOT_FOUND);
        }
        return users.stream().map(userDTOMapper).toList();
    }

}
