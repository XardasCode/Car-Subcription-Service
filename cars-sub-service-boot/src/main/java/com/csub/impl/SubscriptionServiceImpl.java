package com.csub.impl;

import com.csub.dao.SubscriptionDAO;
import com.csub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDAO subscriptionDAO;

}
