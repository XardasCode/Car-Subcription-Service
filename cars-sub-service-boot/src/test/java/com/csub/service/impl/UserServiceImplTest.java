package com.csub.service.impl;


import com.csub.controller.request.UserRequestDTO;
import com.csub.dao.UserDAO;
import com.csub.dto.UserDTO;
import com.csub.dto.mapper.UserDTOMapper;
import com.csub.entity.Subscription;
import com.csub.entity.User;
import com.csub.entity.UserRole;
import com.csub.util.EmailSender;
import com.csub.util.PasswordManager;
import com.csub.util.UserRolesList;
import com.csub.util.UserSearchInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDAO userDAO;
    @Mock
    private  EmailSender emailSender;
    private final UserDTOMapper userDTOMapper = new UserDTOMapper();
    @Mock
    private  PasswordManager passwordManager;

    User user;
    UserRole userRole;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDAO, userDTOMapper, emailSender,passwordManager);
        Subscription subscription = Subscription.builder().build();
        userRole = UserRole.builder().name("USER").build();
        user = User.builder()
                .id(0)
                .name("Fogell")
                .surname("McLovin")
                .email("McLovin3000@gmail.com")
                .password("DTlpxUE8OnvOh96bMNDYDHIdwTes0oLFP6pzA5cZSB7SGB9jU+eWJmeKNRUK0Np6")
                .phone("0985491837")
                .isVerified(false)
                .isBlocked(false)
                .verificationCode(null)
                .role(userRole)
                .build();
    }

    @DisplayName("getUsers must return list of users")
    @Test
    void getUsers() {
        List<User> list = new ArrayList<>();
        list.add(user);
        UserSearchInfo userSearchInfo = UserSearchInfo.builder().build();
        Mockito.when(userDAO.getUsers(userSearchInfo)).thenReturn(list);
        List<UserDTO> actual = userService.getUsers(userSearchInfo);
        Mockito.verify(userDAO, Mockito.times(1)).getUsers(userSearchInfo);
        assertAll(() -> {
            assertEquals(user.getId(), actual.get(0).id());
            assertEquals(user.getName(), actual.get(0).name());
        });
    }

    @DisplayName("addUser must return valid generated id when user is valid")
    @Test
    void addUser() {

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();

        Mockito.when(passwordManager.encryptPassword(user.getPassword())).thenReturn(user.getPassword());
        Mockito.when(userDAO.addUser(user)).thenReturn(OptionalLong.of(user.getId()));
        Mockito.when(userDAO.getRoleById(UserRolesList.USER.getRoleId())).thenReturn(userRole);

        long actual = userService.addUser(userRequestDTO);
        Mockito.verify(userDAO, Mockito.times(1)).addUser(user);
        assertEquals(0, actual);
    }

    @DisplayName("addUser must throw ServerException when user is not valid")
    @Test
    void addUserMustThrowServerException() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();
        User empryUser = new User();
        Mockito.when(passwordManager.encryptPassword(user.getPassword())).thenReturn(user.getPassword());
        Mockito.when(userDAO.addUser(user)).thenReturn(OptionalLong.empty());
        Mockito.when(userDAO.getRoleById(UserRolesList.USER.getRoleId())).thenReturn(userRole);

        assertThatThrownBy(() -> userService.addUser(userRequestDTO))
                .hasMessage("User not added");
        Mockito.verify(userDAO, Mockito.times(1)).addUser(user);
    }

    @DisplayName("getUser must return valid user when id is valid")
    @Test
    void getUser() {
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        UserDTO actual = userService.getUser(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).getUser(user.getId());
        assertAll(() -> {
            assertEquals(user.getId(), actual.id());
            assertEquals(user.getName(), actual.name());
        });
    }

    @DisplayName("getUser must throw ServerException when user is not valid")
    @Test
    void getUserMustThrowServerException() {
        Mockito.when(userDAO.getUser(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUser(user.getId()))
                .hasMessage("User with id "+String.valueOf(user.getId())+" not found");
        Mockito.verify(userDAO, Mockito.times(1)).getUser(user.getId());
    }

    @DisplayName("checkUserCredentials must return valid user when email and password are valid")
    @Test
    void checkUserCredentials() {
        Mockito.when(userDAO.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDTO actual = userService.checkUserCredentials(user.getEmail(), "enscryptTest");
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(user.getEmail());
        assertAll(() -> {
            assertEquals(user.getId(), actual.id());
            assertEquals(user.getName(), actual.name());
        });
    }

    @DisplayName("checkUserCredential must throw ServerException when email is not valid")
    @Test
    void checkUserCredentialsMustThrowServerException1() {
        Mockito.when(userDAO.getUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.checkUserCredentials(user.getEmail(), user.getPassword()))
                .hasMessage("User with email " + user.getEmail() + " or password " + user.getPassword() + " not found");
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(user.getEmail());
    }

    @DisplayName("checkUserCredential must throw ServerException when password is not valid")
    @Test
    void checkUserCredentialsMustThrowServerException2() {
        Mockito.when(userDAO.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userService.checkUserCredentials(user.getEmail(), "wrongPassword"))
                .hasMessage("User with email " + user.getEmail() + " or password wrongPassword not found");
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(user.getEmail());
    }

    @DisplayName("checkUserCredential must throw ServerException when user with email already exists")
    @Test
    void checkIfEmailAlreadyExistsMustThrowServerException() {
        User userUpdate = User.builder()
                .id(1)
                .name("Fogell")
                .surname("McLovin")
                .email("newEmail@gmail.com")
                .password("DTlpxUE8OnvOh96bMNDYDHIdwTes0oLFP6pzA5cZSB7SGB9jU+eWJmeKNRUK0Np6")
                .phone("0985491837")
                .isVerified(false)
                .isBlocked(false)
                .verificationCode(null)
                .build();
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .name(userUpdate.getName())
                .surname(userUpdate.getSurname())
                .email(userUpdate.getEmail())
                .phone(userUpdate.getPhone())
                .password(userUpdate.getPassword())
                .build();
        Mockito.when(userDAO.getUser(anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(Optional.of(userUpdate));
        assertThatThrownBy(() -> userService.updateUser(userRequestDTO, userUpdate.getId()))
                .hasMessage("User with email " + userUpdate.getEmail() + " already exists");
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(userUpdate.getEmail());
    }

    @DisplayName("updateUser checks if the userDao method is called")
    @Test
    void updateUser() {
        Mockito.doNothing().when(userDAO).updateUser(any());
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();
        userService.updateUser(userRequestDTO, user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).updateUser(user);
    }

    @DisplayName("deleteUser checks if the userDao method is called")
    @Test
    void deleteUser() {
        Mockito.doNothing().when(userDAO).deleteUser(anyLong());
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).deleteUser(user.getId());
    }

    @DisplayName("blockUser checks if the userDao method is called")
    @Test
    void blockUser() {
        Mockito.doNothing().when(userDAO).updateUser(user);
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        userService.blockUser(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).updateUser(user);
    }

    @DisplayName("unblockUser checks if the userDao method is called")
    @Test
    void unblockUser() {
        Mockito.doNothing().when(userDAO).updateUser(user);
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        userService.unblockUser(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).updateUser(user);
    }

    @DisplayName("generateVerificationCode checks if the userDao method  and emailSender method is called")
    @Test
    void generateVerificationCode() {

        Mockito.doNothing().when(emailSender).sendEmail(any(),any(),any());
        Mockito.doNothing().when(userDAO).updateUser(user);
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        userService.generateVerificationCode(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).updateUser(user);
        Mockito.verify(emailSender, Mockito.times(1)).sendEmail(any(),any(),any());
    }

    @DisplayName("generateVerificationCode must throw ServerException when user is Verified")
    @Test
    void generateVerificationCodeEx() {

        user.setVerified(true);
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userService.generateVerificationCode(user.getId()))
                .hasMessage("User with id " + user.getId() + " is already verified");
    }

    @DisplayName("verifyEmailThrowException must throw ServerException when user VerificationCode is null")
    @Test
    void verifyEmailThrowException() {
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userService.verifyEmail(user.getId(),"123"))
                .hasMessage("User with id " + user.getId() + " has no verification code");
    }

    @DisplayName("verifyEmailThrowException2 must throw ServerException when user VerificationCode is not equals code")
    @Test
    void verifyEmailThrowException2() {
        user.setVerificationCode("123");
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userService.verifyEmail(user.getId(),"124"))
                .hasMessage("User with id " + user.getId() + " has wrong verification code");
    }

    @DisplayName("verifyEmail must update user Verified to true")
    @Test
    void verifyEmail() {
        Mockito.doNothing().when(userDAO).updateUser(user);
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        user.setVerificationCode("123");
        userService.verifyEmail(user.getId(),"123");
        Mockito.verify(userDAO, Mockito.times(1)).updateUser(user);
    }


}