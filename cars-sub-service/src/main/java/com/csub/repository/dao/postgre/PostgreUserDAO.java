package com.csub.repository.dao.postgre;

import com.csub.entity.User;
import com.csub.repository.dao.UserDAO;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostgreUserDAO implements UserDAO {

    SessionFactory sessionFactory;

    @Autowired
    public PostgreUserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional
    public List<User> getAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    public void deleteUser(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public void updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    @Transactional
    public Optional<User> getUser(long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(User.class, id));
    }
}
