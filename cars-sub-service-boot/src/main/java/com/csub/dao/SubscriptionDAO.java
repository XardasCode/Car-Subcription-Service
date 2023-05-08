package com.csub.dao;

import com.csub.entity.Subscription;
import com.csub.entity.SubscriptionStatus;
import com.csub.util.SubscriptionSearchInfo;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public interface SubscriptionDAO {
    OptionalLong addSubscription(Subscription subscription);

    void deleteSubscription(long id);

    void updateSubscription(Subscription subscription);

    Optional<Subscription> getSubscription(long id);

    List<Subscription> getAllSubscription();

    List<Subscription> searchSubscription(SubscriptionSearchInfo info);

    Optional<SubscriptionStatus> getSubscriptionStatus(int id);
}
