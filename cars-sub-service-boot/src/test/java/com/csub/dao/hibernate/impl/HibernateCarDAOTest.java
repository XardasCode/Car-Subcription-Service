package com.csub.dao.hibernate.impl;

import com.csub.entity.*;
import com.csub.util.CarSearchInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.csub.dao.hibernate.impl")
@TestPropertySource(locations = "classpath:application-test.properties")
class HibernateCarDAOTest {

    @Autowired
    private HibernateCarDAO hibernateCarDAO;

    @Autowired
    private TestEntityManager entityManager;

    Car car = new Car();

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("Test")
                .build();
        CarStatus carStatus = CarStatus.builder()
                .name("AVAILABLE")
                .build();
        car = Car.builder()
                .name("Test car")
                .carStatus(carStatus)
                .build();

        entityManager.persist(user);
        entityManager.persist(carStatus);
        entityManager.persist(car);
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        if (entityManager.find(Car.class, car.getId()) != null) {
            entityManager.remove(car);
        }
    }

    @DisplayName("Get car by id must return car")
    @Test
    void getCarById() {
        Car carFromDB = hibernateCarDAO.getCar(car.getId()).orElseThrow();
        assertThat(carFromDB.getId()).isEqualTo(car.getId());
    }

    @DisplayName("Get car by id must return empty optional")
    @Test
    void getCarByIdEmpty() {
        assertThat(hibernateCarDAO.getCar(0)).isEmpty();
    }

    @DisplayName("Add car must not throw exception")
    @Test
    void addCar() {
        CarStatus carStatus = CarStatus.builder()
                .name("Test")
                .build();
        Car car = Car.builder()
                .name("Test car")
                .carStatus(carStatus)
                .build();
        hibernateCarDAO.addCar(car);
        assertThat(hibernateCarDAO.getCar(car.getId())).isNotEmpty();
    }

    @DisplayName("Update car must not throw exception")
    @Test
    void updateCar() {
        CarStatus carStatus = CarStatus.builder()
                .name("Test")
                .build();
        Car car = Car.builder()
                .name("Test car")
                .carStatus(carStatus)
                .build();
        hibernateCarDAO.addCar(car);
        car.setName("Test car 2");
        hibernateCarDAO.updateCar(car);
        assertThat(hibernateCarDAO.getCar(car.getId()).orElseThrow().getName()).isEqualTo("Test car 2");
    }

    @DisplayName("Delete car must not throw exception")
    @Test
    void deleteCar() {
        CarStatus carStatus = CarStatus.builder()
                .name("Test")
                .build();
        Car car = Car.builder()
                .name("Test car")
                .carStatus(carStatus)
                .build();
        hibernateCarDAO.addCar(car);
        hibernateCarDAO.deleteCar(car.getId());
        assertThat(hibernateCarDAO.getCar(car.getId())).isEmpty();
    }

    @DisplayName("Get cars must return list of cars")
    @Test
    void getCars() {
        CarSearchInfo carSearchInfo = CarSearchInfo.builder()
                .size(10)
                .page(1)
                .direction("DESC")
                .sortField("id")
                .build();
        List<Car> cars = hibernateCarDAO.getCars(carSearchInfo);
        assertThat(cars).isNotEmpty();
    }

    @DisplayName("Get cars count must return count of cars")
    @Test
    void getCarsCount() {
        long count = hibernateCarDAO.getCarsCount(12, List.of("id"));
        assertThat(count).isPositive();
    }

    @DisplayName("Get car image URL must return car image URL")
    @Test
    void getCarImage() {
        String url = "test";
        hibernateCarDAO.updateImage(url, car.getId());
        assertThat(hibernateCarDAO.getImageURL(car.getId())).isEqualTo(url);
    }

    @DisplayName("Get car status by id must return car status")
    @Test
    void getCarStatus() {
        CarStatus carStatus = hibernateCarDAO.getCarStatusById(String.valueOf(car.getCarStatus().getId())).orElseThrow();
        assertThat(carStatus.getId()).isEqualTo(car.getCarStatus().getId());
    }


}