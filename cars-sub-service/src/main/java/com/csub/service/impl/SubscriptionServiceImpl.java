package com.csub.service.impl;

import com.csub.repository.dao.SubscriptionDAO;
import com.csub.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    SubscriptionDAO subscriptionDAO;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }
}
