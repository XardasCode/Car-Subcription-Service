package com.csub.dao.postgre.impl;

import com.csub.entity.Subscription;
import com.csub.dao.SubscriptionDAO;
import jakarta.persistence.EntityManager;
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

    private final EntityManager sessionFactory;

    @Override
    public long addSubscription(Subscription subscription) {
        log.debug("Adding subscription: {}", subscription);
        sessionFactory.persist(subscription);
        log.debug("Subscription created with id {}", subscription.getId());
        return subscription.getId();
    }

    @Override
    public void deleteSubscription(long id) {
        log.debug("Delete subscription with id: {}", id);
        Subscription subscription = sessionFactory.find(Subscription.class, id);
        if (subscription != null) sessionFactory.remove(subscription);
        log.debug("Subscription deleted with id {}", id);
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        log.debug("Updating subscription: {}", subscription);
        sessionFactory.merge(subscription);
        log.debug("Subscription updated: {}", subscription);
    }

    @Override
    public Optional<Subscription> getSubscription(long id) {
        log.debug("Getting subscription with id {}", id);
        return Optional.ofNullable(sessionFactory.find(Subscription.class, id));
    }

    @Override
    public List<Subscription> getAllSubscription() {
        log.debug("Getting all users");
        return sessionFactory.createQuery("from Subscription", Subscription.class).getResultList();
    }

    @Override
    public Optional<Subscription> getSubscriptionsByUserId(long id) {
        log.debug("Getting subscription with user id {}", id);
        return sessionFactory.createQuery("from Subscription where Subscription.user.id = :id", Subscription.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();

    }
}
