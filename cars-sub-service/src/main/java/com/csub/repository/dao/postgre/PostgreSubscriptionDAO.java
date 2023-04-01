package com.csub.repository.dao.postgre;

import com.csub.entity.Subscription;
import com.csub.repository.dao.SubscriptionDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostgreSubscriptionDAO implements SubscriptionDAO {

    SessionFactory sessionFactory;

    @Autowired
    public PostgreSubscriptionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addSubscription(Subscription subscription) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public void deleteSubscription(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }

    @Override
    public Subscription getSubscription(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // todo: implement this method
    }
}
