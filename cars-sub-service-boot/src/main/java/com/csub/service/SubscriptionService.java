package com.csub.service;

import com.csub.dto.SubscriptionDTO;
import com.csub.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    long addSubscription(Subscription subscription);


    SubscriptionDTO getSubscription(long id);


    void updateSubscription(Subscription subscription, long id);

    void deleteSubscription(long id);


    List<SubscriptionDTO> getAllSubscriptions();

    List<SubscriptionDTO> getSubscriptionsByUserId(long id);
}
