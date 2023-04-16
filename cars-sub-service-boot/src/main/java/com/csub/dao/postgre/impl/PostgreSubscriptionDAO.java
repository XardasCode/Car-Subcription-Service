package com.csub.dao.postgre.impl;

import com.csub.entity.Subscription;
import com.csub.dao.SubscriptionDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreSubscriptionDAO implements SubscriptionDAO {

    private final SessionFactory sessionFactory;

    @Override
    public long addSubscription(Subscription subscription) {
        log.debug("Adding subscription: {}", subscription);
        sessionFactory.getCurrentSession().persist(subscription);
        log.debug("Subscription created with id {}", subscription.getId());
        return subscription.getId();
    }

    @Override
    public void deleteSubscription(long id) {
        log.debug("Delete subscription with id: {}", id);
        Subscription subscription = sessionFactory.getCurrentSession().get(Subscription.class, id);
        if (subscription != null) sessionFactory.getCurrentSession().remove(subscription);
        log.debug("Subscription deleted with id {}", id);
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        log.debug("Updating subscription: {}", subscription);
        sessionFactory.getCurrentSession().merge(subscription);
        log.debug("Subscription updated: {}", subscription);
    }

    @Override
    public Optional<Subscription> getSubscription(long id) {
        log.debug("Getting subscription with id {}", id);
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Subscription.class, id));
    }

    @Override
    public List<Subscription> getAllSubscription() {
        log.debug("Getting all users");
        return sessionFactory.getCurrentSession().createQuery("from Subscription", Subscription.class).list();
    }
}
