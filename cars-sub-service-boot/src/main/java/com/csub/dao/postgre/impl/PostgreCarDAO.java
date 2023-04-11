package com.csub.dao.postgre.impl;

import com.csub.entity.Car;
import com.csub.dao.CarDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@Slf4j
public class PostgreCarDAO implements CarDAO {

    private final SessionFactory sessionFactory;

    @Override
    public void addCar(Car car) {
        log.debug("Adding car: {}", car);
        sessionFactory.getCurrentSession().persist(car);
        log.debug("Car created with id {}", car.getId());
    }

    @Override
    public void deleteCar(long id) {
        log.debug("Delete car with id: {}", id);
        Car car = sessionFactory.getCurrentSession().get(Car.class, id);
        if (car != null) sessionFactory.getCurrentSession().remove(car);
        log.debug("Car deleted with id {}", id);
    }

    @Override
    public void updateCar(Car car) {
        log.debug("Updating car: {}", car);
        sessionFactory.getCurrentSession().merge(car);
        log.debug("Car updated: {}", car);
    }

    @Override
    public Optional<Car> getCar(long id) {
        log.debug("Getting car with id {}", id);
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Car.class, id));
    }

    @Override
    public List<Car> getAllCars() {
        log.debug("Getting all cars");
        return sessionFactory.getCurrentSession().createQuery("from Car", Car.class).list();
    }
}
