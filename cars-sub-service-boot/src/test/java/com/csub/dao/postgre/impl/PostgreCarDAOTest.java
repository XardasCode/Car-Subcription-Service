package com.csub.dao.postgre.impl;

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
@ComponentScan(basePackages = "com.csub.dao.postgre.impl")
@TestPropertySource(locations = "classpath:application-test.properties")
class PostgreCarDAOTest {

    @Autowired
    private PostgreCarDAO postgreCarDAO;

    @Autowired
    private TestEntityManager entityManager;

    Car car = new Car();

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("Test")
                .build();
        Manager manager = Manager.builder()
                .name("Test")
                .build();
        Subscription subscription = Subscription.builder()
                .user(user)
                .manager(manager)
                .build();
        CarStatus carStatus = CarStatus.builder()
                .name("Test")
                .build();
        car = Car.builder()
                .name("Test car")
                .subscription(subscription)
                .carStatus(carStatus)
                .build();

        entityManager.persist(user);
        entityManager.persist(manager);
        entityManager.persist(subscription);
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
        Car carFromDB = postgreCarDAO.getCar(car.getId()).orElseThrow();
        assertThat(carFromDB.getId()).isEqualTo(car.getId());
    }

    @DisplayName("Get car by id must return empty optional")
    @Test
    void getCarByIdEmpty() {
        assertThat(postgreCarDAO.getCar(0)).isEmpty();
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
        postgreCarDAO.addCar(car);
        assertThat(postgreCarDAO.getCar(car.getId())).isNotEmpty();
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
        postgreCarDAO.addCar(car);
        car.setName("Test car 2");
        postgreCarDAO.updateCar(car);
        assertThat(postgreCarDAO.getCar(car.getId()).orElseThrow().getName()).isEqualTo("Test car 2");
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
        postgreCarDAO.addCar(car);
        postgreCarDAO.deleteCar(car.getId());
        assertThat(postgreCarDAO.getCar(car.getId())).isEmpty();
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
        List<Car> cars = postgreCarDAO.getCars(carSearchInfo);
        assertThat(cars).isNotEmpty();
    }

    @DisplayName("Get cars count must return count of cars")
    @Test
    void getCarsCount() {
        long count = postgreCarDAO.getCarsCount(12, List.of("id"));
        assertThat(count).isPositive();
    }

    @DisplayName("Get car image URL must return car image URL")
    @Test
    void getCarImage() {
        String url = "test";
        postgreCarDAO.updateImage(url, car.getId());
        assertThat(postgreCarDAO.getImageURL(car.getId())).isEqualTo(url);
    }

    @DisplayName("Get car status by id must return car status")
    @Test
    void getCarStatus() {
        CarStatus carStatus = postgreCarDAO.getCarStatusById(String.valueOf(car.getCarStatus().getId())).orElseThrow();
        assertThat(carStatus.getId()).isEqualTo(car.getCarStatus().getId());
    }


}