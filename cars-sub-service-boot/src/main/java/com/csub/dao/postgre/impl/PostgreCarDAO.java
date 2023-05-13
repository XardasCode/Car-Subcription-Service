package com.csub.dao.postgre.impl;

import com.csub.dao.postgre.util.CarCriteriaBuilderManager;
import com.csub.entity.Car;
import com.csub.dao.CarDAO;
import com.csub.entity.CarStatus;
import com.csub.util.CarSearchInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@Slf4j
public class PostgreCarDAO implements CarDAO {

    private final EntityManager sessionFactory;

    @Override
    public void addCar(Car car) {
        log.debug("Adding car: {}", car);
        sessionFactory.persist(car);
        log.debug("Car added: {}", car);
    }

    @Override
    public void deleteCar(long id) {
        log.debug("Delete car with id: {}", id);
        Car car = sessionFactory.find(Car.class, id);
        if (car != null) sessionFactory.remove(car);
        log.debug("Car deleted with id {}", id);
    }

    @Override
    public void updateCar(Car car) {
        log.debug("Updating car: {}", car);
        sessionFactory.merge(car);
        log.debug("Car updated: {}", car);
    }

    @Override
    public Optional<Car> getCar(long id) {
        log.debug("Getting car with id {}", id);
        return Optional.ofNullable(sessionFactory.find(Car.class, id));
    }

    @Override
    public List<Car> getCars(CarSearchInfo info) {
        log.debug("Getting all cars with search info: {}", info);
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);

        List<Predicate> predicates = CarCriteriaBuilderManager.buildCriteria(info, builder, query, root);

        int offset = (info.getPage() - 1) * info.getSize();
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Car> typedQuery = sessionFactory.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(info.getSize());
        return typedQuery.getResultList();
    }

    @Override
    public int getCarsCount(int size, List<String> filter) {
        log.debug("Getting cars count with size {} and filter {}", size, filter);
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);

        List<Predicate> predicates = CarCriteriaBuilderManager.buildCountCriteria(size, filter, builder, root);

        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Car> typedQuery = sessionFactory.createQuery(query);
        return typedQuery.getResultList().size();
    }

    @Override
    public void updateImage(String imagePath, long carID) {
        log.debug("Updating image path for car with id {}", carID);
        sessionFactory.createQuery("UPDATE Car c SET c.imagePath = :imagePath WHERE c.id = :carId")
                .setParameter("imagePath", imagePath)
                .setParameter("carId", carID)
                .executeUpdate();
    }

    @Override
    public String getImageURL(long carId) {
        log.debug("Getting image path for car with id {}", carId);
        return sessionFactory.createQuery("SELECT c.imagePath FROM Car c WHERE c.id = :carId", String.class)
                .setParameter("carId", carId)
                .getSingleResult();
    }

    @Override
    public Optional<CarStatus> getCarStatusById(String statusId) {
        log.debug("Getting car status with id {}", statusId);
        int id = Integer.parseInt(statusId);
        return Optional.ofNullable(sessionFactory.find(CarStatus.class, id));
    }

    @Override
    public Optional<CarStatus> getCarStatusById(int statusId) {
        log.debug("Getting car status with id {}", statusId);
        return Optional.ofNullable(sessionFactory.find(CarStatus.class, statusId));
    }
}
