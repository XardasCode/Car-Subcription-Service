package com.csub.dao.postgre.impl;

import com.csub.entity.*;
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
@ComponentScan(basePackages = "com.csub.dao.postgre.impl")
@TestPropertySource(locations = "classpath:application-test.properties")
class PostgreManagerDAOTest {

    @Autowired
    private PostgreManagerDAO postgreCarDAO;

    @Autowired
    private TestEntityManager entityManager;

    Manager manager;

    @BeforeEach
    void setUp() {
        manager = Manager.builder()
                .name("Test")
                .email("some_mail@mail.com")
                .build();
        entityManager.persist(manager);
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        if (entityManager.find(Manager.class, manager.getId()) != null) {
            entityManager.remove(manager);
        }
    }

    @DisplayName("Add manager should add manager and not throw exception")
    @Test
    void addManager() {
        assertThatCode(() -> postgreCarDAO.addManager(manager))
                .doesNotThrowAnyException();
    }

    @DisplayName("Get manager by id should return manager")
    @Test
    void getManagerById() {
        assertThat(postgreCarDAO.getManager(manager.getId()).orElseThrow()).isEqualTo(manager);
    }

    @DisplayName("Get manager by id should return empty optional")
    @Test
    void getManagerByIdEmpty() {
        assertThat(postgreCarDAO.getManager(0L)).isEmpty();
    }

    @DisplayName("Delete manager should delete manager and not throw exception")
    @Test
    void deleteManager() {
        assertThatCode(() -> postgreCarDAO.deleteManager(1))
                .doesNotThrowAnyException();
    }

    @DisplayName("Get all managers should return list of managers")
    @Test
    void getAllManagers() {
        assertThat(postgreCarDAO.getAllManagers()).containsExactly(manager);
    }

    @DisplayName("Get manager by email should return manager")
    @Test
    void getManagerByEmail() {
        assertThat(postgreCarDAO.getManagerByEmail(manager.getEmail()).orElseThrow()).isEqualTo(manager);
    }

    @DisplayName("Update manager should update manager and not throw exception")
    @Test
    void updateManager() {
        manager.setName("New name");
        assertThatCode(() -> postgreCarDAO.updateManager(manager))
                .doesNotThrowAnyException();
    }


}