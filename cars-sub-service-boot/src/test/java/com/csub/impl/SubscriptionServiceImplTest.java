package com.csub.impl;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.dao.SubscriptionDAO;
import com.csub.dto.SubscriptionDTO;
import com.csub.dto.mapper.SubscriptionDTOMapper;
import com.csub.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    @Mock
    private SubscriptionDAO subscriptionDAO;

    private final SubscriptionDTOMapper subscriptionDTOMapper = new SubscriptionDTOMapper();

    SubscriptionRequestDTO subscriptionRequestDTO;
    User user;
    Subscription subscription;
    Car car;
    Manager manager;

    @BeforeEach
    void setUp() {
        subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, subscriptionDTOMapper);
        CarStatus carStatus = new CarStatus();
        SubscriptionStatus status = new SubscriptionStatus();
        status.setId(1);
        user = User.builder()
                .id(1)
                .name("Fogell")
                .surname("McLovin")
                .email("McLovin3000@gmail.com")
                .password("DTlpxUE8OnvOh96bMNDYDHIdwTes0oLFP6pzA5cZSB7SGB9jU+eWJmeKNRUK0Np6")
                .phone("0985491837")
                .isVerified(false)
                .isBlocked(false)
                .build();
        car = Car.builder()
                .id(1)
                .name("Camry")
                .model("3.5 6AT Premium")
                .brand("Toyota ")
                .year(2019)
                .color("Black")
                .price(2250)
                .fuelType("Gasoline")
                .chassisNumber("9591")
                .regNumber("TT")
                .regDate("25.05.2020")
                .mileage(11230)
                .lastServiceDate("05.10.2022")
                .statusId(1)
                .carStatus(carStatus)
                .build();
        manager = Manager.builder()
                .id(1)
                .name("John")
                .surname("Wick")
                .email("killer2077@gmail.com")
                .password("TheLordOfDarkness")
                .build();
        subscription = Subscription.builder()
                .id(1)
                .isActive(true)
                .startDate("21.04.2023")
                .monthPrice(car.getPrice())
                .totalMonths(5)
                .totalPrice(5 * car.getPrice())
                .manager(manager)
                .user(user)
                .car(car)
                .status(status)
                .build();
        subscriptionRequestDTO = SubscriptionRequestDTO.builder()
                .isActive("True")
                .startDate("2022-01-01")
                .totalMonths(String.valueOf(subscription.getTotalMonths()))
                .monthPrice(String.valueOf(subscription.getMonthPrice()))
                .totalPrice(String.valueOf(subscription.getTotalPrice()))
                .manager_id(String.valueOf(manager.getId()))
                .car_id(String.valueOf(car.getId()))
                .status_id(String.valueOf(status.getId()))
                .user_id(String.valueOf(user.getId()))
                .build();
    }

    @DisplayName("addSubscription must return valid generated id when subscription is valid")
    @Test
    void addSubscription() {
        Mockito.when(subscriptionDAO.addSubscription(subscription)).thenReturn(Long.valueOf(subscription.getId()));
        long actual = subscriptionService.addSubscription(subscriptionRequestDTO);
        Mockito.verify(subscriptionDAO, Mockito.times(1)).addSubscription(subscription);
        assertEquals(1, actual);
    }

    @DisplayName("addSubscription must throw ServerException when subscription is not valid")
    @Test
    void addSubscriptionMustThrowServerException() {
        Mockito.when(subscriptionDAO.addSubscription(subscription)).thenReturn(0L);
        assertThatThrownBy(() -> subscriptionService.addSubscription(subscriptionRequestDTO))
                .hasMessage("Subscription not added");
        Mockito.verify(subscriptionDAO, Mockito.times(1)).addSubscription(subscription);
    }

    @DisplayName("getSubscription must return valid subscription when id is valid")
    @Test
    void getSubscription() {
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        SubscriptionDTO actual = subscriptionService.getSubscription(subscription.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).getSubscription(subscription.getId());
        assertAll(() -> {
            assertEquals(subscription.getId(), actual.id());
            assertEquals(subscription.isActive(), actual.is_active());
        });
    }

    @DisplayName("getSubscription must throw ServerException when subscription id is not valid")
    @Test
    void getSubscriptionMustThrowServerException() {
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> subscriptionService.getSubscription(subscription.getId()))
                .hasMessage("Subscription not found");
        Mockito.verify(subscriptionDAO, Mockito.times(1)).getSubscription(subscription.getId());
    }

    @DisplayName("updateSubscription checks if the carDao method is called")
    @Test
    void updateSubscription() {
        Mockito.doNothing().when(subscriptionDAO).updateSubscription(any());
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        subscriptionService.updateSubscription(subscriptionRequestDTO, subscription.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).updateSubscription(subscription);
    }

    @DisplayName("deleteSubscription checks if the carDao method is called")
    @Test
    void deleteSubscription() {
        Mockito.doNothing().when(subscriptionDAO).deleteSubscription(subscription.getId());
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        subscriptionService.deleteSubscription(subscription.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).deleteSubscription(subscription.getId());
    }

    @DisplayName("getSubscriptionsByUserId must return valid list of subscriptions when user id is valid")
    @Test
    void getSubscriptionsByUserId() {
        Mockito.when(subscriptionDAO.getSubscriptionsByUserId(subscription.getUser().getId())).thenReturn(Optional.of(subscription));
        SubscriptionDTO actual = subscriptionService.getSubscriptionByUserId(subscription.getUser().getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).getSubscriptionsByUserId(subscription.getUser().getId());
        assertAll(() -> {
            assertEquals(subscription.getId(), actual.id());
            assertEquals(subscription.isActive(), actual.is_active());
        });
    }
}