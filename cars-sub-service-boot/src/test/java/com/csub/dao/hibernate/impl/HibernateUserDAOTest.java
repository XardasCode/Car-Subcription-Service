package com.csub.dao.hibernate.impl;

import com.csub.entity.User;
import com.csub.util.UserSearchInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.csub.dao.hibernate.impl")
@TestPropertySource(locations = "classpath:application-test.properties")
class HibernateUserDAOTest {

    @Autowired
    private HibernateUserDAO hibernateUserDAO;

    @Autowired
    private TestEntityManager entityManager;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Test")
                .surname("Test")

                .email("mail@gmail.com")
                .build();

        entityManager.persist(user);
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        if (entityManager.find(User.class, user.getId()) != null) {
            entityManager.remove(user);
        }
    }

    @DisplayName("Get user by id must return user")
    @Test
    void getUserById() {
        assertThat(hibernateUserDAO.getUser(user.getId()).orElseThrow())
                .isEqualTo(user);
    }

    @DisplayName("Add user must not throw exception")
    @Test
    void addUser() {
        User user = User.builder()
                .name("Test")
                .build();
        hibernateUserDAO.addUser(user);
        assertThat(hibernateUserDAO.getUser(user.getId()).orElseThrow())
                .isEqualTo(user);
    }

    @DisplayName("Update user must not throw exception")
    @Test
    void updateUser() {
        User user = User.builder()
                .name("Test")
                .build();
        hibernateUserDAO.addUser(user);
        user.setName("Test2");
        hibernateUserDAO.updateUser(user);
        assertThat(hibernateUserDAO.getUser(user.getId()).orElseThrow())
                .isEqualTo(user);
    }

    @DisplayName("Delete user must not throw exception")
    @Test
    void deleteUser() {
        User user = User.builder()
                .name("Test")
                .build();
        hibernateUserDAO.addUser(user);
        hibernateUserDAO.deleteUser(user.getId());
        assertThat(hibernateUserDAO.getUser(user.getId())).isEmpty();
    }

    @DisplayName("Get user by email must return user")
    @Test
    void getUserByEmail() {
        assertThat(hibernateUserDAO.getUserByEmail(user.getEmail()).orElseThrow())
                .isEqualTo(user);
    }

    @DisplayName("Get all users must return not empty list")
    @Test
    void searchUsers() {
        UserSearchInfo userSearchInfo = UserSearchInfo.builder()
                .page(1)
                .size(12)
                .build();
        assertThat(hibernateUserDAO.getUsers(userSearchInfo)).isNotEmpty();
    }

    @DisplayName("Find users must return not empty list")
    @Test
    void findUsers() {
        assertThat(hibernateUserDAO.findUsers("Test", "Test", true, "ASC")).isNotEmpty();
    }


}