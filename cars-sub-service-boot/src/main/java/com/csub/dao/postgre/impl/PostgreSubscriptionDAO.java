package com.csub.dao.postgre.impl;

import com.csub.entity.Subscription;
import com.csub.dao.SubscriptionDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreSubscriptionDAO implements SubscriptionDAO {

    private final SessionFactory sessionFactory;

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
