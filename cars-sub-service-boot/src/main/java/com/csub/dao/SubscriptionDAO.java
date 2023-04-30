package com.csub.dao;

import com.csub.entity.Subscription;
import com.csub.entity.SubscriptionStatus;
import com.csub.util.SubscriptionSearchInfo;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDAO {
    long addSubscription(Subscription subscription);

    void deleteSubscription(long id);

    void updateSubscription(Subscription subscription);

    Optional<Subscription> getSubscription(long id);

    List<Subscription> getAllSubscription();

    Optional<Subscription> getSubscriptionsByUserId(long id);

    Optional<SubscriptionStatus> getSubscriptionStatusById(String statusId);

    List<Subscription> searchSubscription(SubscriptionSearchInfo info);
}
