package com.csub.repository.dao.postgre;

import com.csub.entity.Car;
import com.csub.repository.dao.CarDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreCarDAO implements CarDAO {

    private final SessionFactory sessionFactory;

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
