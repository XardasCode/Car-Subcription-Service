package com.csub.impl;

import com.csub.dao.SubscriptionDAO;
import com.csub.entity.Subscription;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDAO subscriptionDAO;

    @Override
    @Transactional
    public long addSubscription(Subscription subscription) {
        log.debug("Adding subscription: {}", subscription);

        long id = subscriptionDAO.addSubscription(subscription);
        if (id == 0) {
            log.warn("Subscription not added");
            throw new ServerException("Subscription not added", ErrorList.SUBSCRIPTION_ALREADY_EXISTS);
        }
        log.debug("Subscription added: {}", subscription);
        return id;
    }

    @Override
    @Transactional
    public Subscription getSubscription(long id) {
        log.debug("Getting subscription with id {}", id);
        Optional <Subscription> subscription = subscriptionDAO.getSubscription((int) id);
        if (subscription.isEmpty()) {
            log.debug("Subscription with id {} not found", id);
            throw new ServerException("Subscription not found", ErrorList.SUBSCRIPTION_NOT_FOUND);
        }
        log.debug("Subscription found: {}", subscription);
        return subscription.get();
    }

    @Override
    @Transactional
    public void updateSubscription(Subscription subscription, long id) {
        log.debug("Updating subscription: {}", subscription);
        getSubscription(id);
        subscriptionDAO.updateSubscription(subscription);
        log.debug("Subscription updated: {}", subscription);
    }

    @Override
    @Transactional
    public void deleteSubscription(long id) {
        log.debug("Deleting subscription with id {}", id);
        getSubscription(id);
        subscriptionDAO.deleteSubscription(id);
        log.debug("Subscription with id {} deleted", id);
    }

    @Override
    @Transactional
    public List<Subscription> getAllSubscription() {
        log.debug("Getting all subscriptions");
        List<Subscription> subscriptions = subscriptionDAO.getAllSubscription();
        log.debug("Subscriptions found: {}", subscriptions.size());
        return subscriptions;
    }
}
