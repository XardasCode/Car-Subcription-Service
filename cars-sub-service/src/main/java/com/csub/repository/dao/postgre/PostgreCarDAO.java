package com.csub.repository.dao.postgre;

import com.csub.entity.Car;
import com.csub.repository.dao.CarDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostgreCarDAO implements CarDAO {

    SessionFactory sessionFactory;

    @Autowired
    public PostgreCarDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCar(Car car) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method

    }

    @Override
    public void deleteCar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public void updateCar(Car car) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public Car getCar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }
}
