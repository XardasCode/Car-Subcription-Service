package com.csub.impl;


import com.csub.controller.request.UserRequestDTO;
import com.csub.dao.UserDAO;
import com.csub.dto.UserDTO;
import com.csub.dto.mapper.UserDTOMapper;
import com.csub.entity.Subscription;
import com.csub.entity.User;
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
    private UserDTOMapper userDTOMapper = new UserDTOMapper();
    User user;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDAO, userDTOMapper);
        Subscription subscription = Subscription.builder().build();
        user = User.builder()
                .id(1)
                .name("Fogell")
                .surname("McLovin")
                .email("McLovin3000@gmail.com")
                .password("DTlpxUE8OnvOh96bMNDYDHIdwTes0oLFP6pzA5cZSB7SGB9jU+eWJmeKNRUK0Np6")
                .phone("0985491837")
                .isVerified(false)
                .isBlocked(false)
                .verificationCode(null)
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
        Mockito.when(userDAO.addUser(user)).thenReturn(OptionalLong.of(user.getId()));
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();
        long actual = userService.addUser(userRequestDTO);
        Mockito.verify(userDAO, Mockito.times(1)).addUser(user);
        assertEquals(1, actual);
    }

    @DisplayName("addUser must throw ServerException when user is not valid")
    @Test
    void addUserMustThrowServerException() {
        Mockito.when(userDAO.addUser(user)).thenReturn(OptionalLong.empty());
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();
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
        assertThatThrownBy(() -> userService.getUser(1))
                .hasMessage("User with id 1 not found");
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
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(Optional.of(userUpdate));
        assertThatThrownBy(() -> userService.updateUser(userRequestDTO, userUpdate.getId()))
                .hasMessage("User with email " + userUpdate.getEmail() + " already exists");
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(userUpdate.getEmail());
    }

    @DisplayName("updateUser checks if the carDao method is called")
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

    @DisplayName("deleteUser checks if the carDao method is called")
    @Test
    void deleteUser() {
        Mockito.doNothing().when(userDAO).deleteUser(anyLong());
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).deleteUser(user.getId());
    }
}