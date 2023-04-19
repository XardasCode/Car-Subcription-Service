package com.csub.impl;

import com.csub.dao.SubscriptionDAO;
import com.csub.dto.mapper.SubscriptionDTOMapper;
import com.csub.entity.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    @Mock
    private SubscriptionDAO subscriptionDAO;
    @Mock
    private SubscriptionDTOMapper subscriptionDTOMapper;



    @BeforeEach
    void setUp()
    {

    }

    @Test
    void addSubscription() {
    }

    @Test
    void getSubscription() {
    }

    @Test
    void updateSubscription() {
    }

    @Test
    void deleteSubscription() {
    }

    @Test
    void getSubscriptionsByUserId() {
    }
}