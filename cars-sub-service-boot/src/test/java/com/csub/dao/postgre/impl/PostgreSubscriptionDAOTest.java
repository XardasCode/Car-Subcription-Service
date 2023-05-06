package com.csub.dao.postgre.impl;

import com.csub.entity.Subscription;
import com.csub.entity.SubscriptionStatus;
import com.csub.entity.User;
import com.csub.util.SubscriptionSearchInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.csub.dao.postgre.impl")
@TestPropertySource(locations = "classpath:application-test.properties")
class PostgreSubscriptionDAOTest {

    @Autowired
    private PostgreSubscriptionDAO postgreCarDAO;

    @Autowired
    private TestEntityManager entityManager;

    Subscription subscription;

    @BeforeEach
    void setUp() {
        SubscriptionStatus subscriptionStatus = SubscriptionStatus.builder()
                .name("Test")
                .build();
        subscription = Subscription.builder()
                .status(subscriptionStatus)
                .build();
        User user = User.builder()
                .name("Test")
                .build();
        subscription.setUser(user);
        user.setSubscription(subscription);

        entityManager.persist(user);
        entityManager.persist(subscriptionStatus);
        entityManager.persist(subscription);
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        if (entityManager.find(Subscription.class, subscription.getId()) != null) {
            entityManager.remove(subscription);
        }
    }

    @DisplayName("Get subscription by id must return subscription")
    @Test
    void getSubscriptionById() {
        Subscription subscriptionFromDB = postgreCarDAO.getSubscription(subscription.getId()).orElseThrow();
        assertThat(subscriptionFromDB).isEqualTo(subscription);
    }

    @DisplayName("Add subscription should add subscription and not throw exception")
    @Test
    void addSubscription() {
        assertThatCode(() -> postgreCarDAO.addSubscription(subscription))
                .doesNotThrowAnyException();
    }

    @DisplayName("Update subscription should update subscription and not throw exception")
    @Test
    void updateSubscription() {
        subscription.setStatus(SubscriptionStatus.builder().name("Test2").build());
        assertThatCode(() -> postgreCarDAO.updateSubscription(subscription))
                .doesNotThrowAnyException();
    }

    @DisplayName("Delete subscription should delete subscription and not throw exception")
    @Test
    void deleteSubscription() {
        assertThatCode(() -> postgreCarDAO.deleteSubscription(1))
                .doesNotThrowAnyException();
    }

    @DisplayName("Get subscription by id should return empty optional")
    @Test
    void getSubscriptionByIdShouldReturnEmptyOptional() {
        assertThat(postgreCarDAO.getSubscription(Integer.MAX_VALUE)).isEmpty();
    }

    @DisplayName("Find subscriptions must return list of subscriptions")
    @Test
    void findSubscriptions() {
        SubscriptionSearchInfo subscriptionSearchInfo = SubscriptionSearchInfo.builder()
                .page(1)
                .size(10)
                .build();
        assertThat(postgreCarDAO.searchSubscription(subscriptionSearchInfo)).isNotEmpty();
    }
}