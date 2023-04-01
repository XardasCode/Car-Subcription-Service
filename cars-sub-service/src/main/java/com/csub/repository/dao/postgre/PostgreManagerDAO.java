package com.csub.repository.dao.postgre;

import com.csub.entity.Manager;
import com.csub.repository.dao.ManagerDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostgreManagerDAO implements ManagerDAO {

    SessionFactory sessionFactory;

    @Autowired
    public PostgreManagerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addManager(Manager manager) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public void deleteManager(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public void updateManager(Manager manager) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public Manager getManager(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }
}
