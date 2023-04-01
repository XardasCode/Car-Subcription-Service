package com.csub.repository.dao;

import com.csub.entity.Subscription;

public interface SubscriptionDAO {
    void addSubscription(Subscription subscription);

    void deleteSubscription(int id);

    void updateSubscription(Subscription subscription);

    Subscription getSubscription(int id);
}
