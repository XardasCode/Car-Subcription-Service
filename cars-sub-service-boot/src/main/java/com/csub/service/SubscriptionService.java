package com.csub.service;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.dto.SubscriptionDTO;
import com.csub.util.SubscriptionSearchInfo;

import java.util.List;

public interface SubscriptionService {

    long addSubscription(SubscriptionRequestDTO subscription);

    SubscriptionDTO getSubscription(long id);

    void updateSubscription(SubscriptionRequestDTO subscription, long id);

    void deleteSubscription(long id);

    List<SubscriptionDTO> getAllSubscriptions();

    List<SubscriptionDTO> searchSubscription(SubscriptionSearchInfo info);

    void confirmSubscription(long id);

    void rejectSubscription(long id);
}
