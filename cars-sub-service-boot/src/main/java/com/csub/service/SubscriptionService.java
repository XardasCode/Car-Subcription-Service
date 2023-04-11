package com.csub.service;

import com.csub.entity.Subscription;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubscriptionService {
    @Transactional
    long addSubscription(Subscription subscription);

    @Transactional
    Subscription getSubscription(long id);

    @Transactional
    void updateSubscription(Subscription subscription, long id);

    @Transactional
    void deleteSubscription(long id);

    @Transactional
    List<Subscription> getAllSubscription();
}
