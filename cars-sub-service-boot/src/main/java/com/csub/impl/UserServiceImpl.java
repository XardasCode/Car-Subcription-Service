package com.csub.impl;

import com.csub.controller.request.UserRequestDTO;
import com.csub.dto.UserDTO;
import com.csub.dto.mapper.UserDTOMapper;
import com.csub.entity.User;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.dao.UserDAO;
import com.csub.service.UserService;
import com.csub.util.EmailSender;
import com.csub.util.UserSearchInfo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.csub.util.PasswordManager.checkPassword;
import static com.csub.util.PasswordManager.encryptPassword;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final UserDTOMapper userDTOMapper;

    private final EmailSender emailSender;

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

    @Override
    @Transactional
    public void blockUser(long id) {
        log.debug("Blocking user with id {}", id);
        Optional<User> optionalUser = userDAO.getUser(id);
        User user = optionalUser.orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
        user.setBlocked(true);
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void unblockUser(long id) {
        log.debug("Unblocking user with id {}", id);
        Optional<User> optionalUser = userDAO.getUser(id);
        User user = optionalUser.orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
        user.setBlocked(false);
        userDAO.updateUser(user);
    }

    @Override
    public void generateVerificationCode(long id) {
        log.debug("Generating verification code for user with id {}", id);
        User optionalUser = userDAO.getUser(id)
                .orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
        if (optionalUser.isVerified()) {
            log.warn("User with id {} is already verified", id);
            throw new ServerException("User with id " + id + " is already verified", ErrorList.USER_VERIFICATION_ERROR);
        }
        String code = UUID.randomUUID().toString();
        optionalUser.setVerificationCode(code);
        userDAO.updateUser(optionalUser);
        emailSender.sendEmail(optionalUser.getEmail(), "Verification code", "Your verification code is: " + code);
        log.debug("Verification code generated for user with id {}", id);
    }

    @Override
    public void verifyEmail(long id, String code) {
        log.debug("Verifying email for user with id {}", id);
        User optionalUser = userDAO.getUser(id)
                .orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
        if (optionalUser.getVerificationCode() == null) {
            log.warn("User with id {} has no verification code", id);
            throw new ServerException("User with id " + id + " has no verification code", ErrorList.USER_VERIFICATION_ERROR);
        }
        if (!optionalUser.getVerificationCode().equals(code)) {
            log.warn("User with id {} has wrong verification code", id);
            throw new ServerException("User with id " + id + " has wrong verification code", ErrorList.USER_VERIFICATION_ERROR);
        }
        optionalUser.setVerificationCode(null);
        optionalUser.setVerified(true);
        userDAO.updateUser(optionalUser);
    }
}
