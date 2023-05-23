package com.csub.service.impl;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dao.SubscriptionDAO;
import com.csub.dao.UserDAO;
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

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    @Mock
    private SubscriptionDAO subscriptionDAO;
    @Mock
    private  CarDAO carDAO;
    @Mock
    private  UserDAO userDAO;

    private final SubscriptionDTOMapper subscriptionDTOMapper = new SubscriptionDTOMapper();

    User user;
    Subscription subscription;
    Car car;
    SubscriptionRequestDTO subscriptionRequestDTO;
    CarStatus carStatus = new CarStatus();
    SubscriptionStatus status = new SubscriptionStatus();

    @BeforeEach
    void setUp() {
//        subscriptionService = new SubscriptionServiceImpl();
//        carStatus.setId(1);
//        carStatus.setName("In stock");
//        status.setId(1);
//        status.setName("Active");
//        user = User.builder()
//                .id(1)
//                .name("Fogell")
//                .surname("McLovin")
//                .email("McLovin3000@gmail.com")
//                .password("DTlpxUE8OnvOh96bMNDYDHIdwTes0oLFP6pzA5cZSB7SGB9jU+eWJmeKNRUK0Np6")
//                .phone("0985491837")
//                .isVerified(false)
//                .isBlocked(false)
//                .build();
//        car = Car.builder()
//                .id(1)
//                .name("Camry")
//                .model("3.5 6AT Premium")
//                .brand("Toyota ")
//                .year(2019)
//                .color("Black")
//                .price(2000)
//                .fuelType("Gasoline")
//                .chassisNumber("9591")
//                .regNumber("TT")
//                .regDate("25.05.2020")
//                .mileage(Integer.parseInt("11230"))
//                .lastServiceDate("05.10.2022")
//                .carStatus(carStatus)
//                .build();
//        subscription = Subscription.builder()
//                .id(1)
//                .isActive(true)
//                .startDate("21.04.2023")
//                .monthPrice(2000)
//                .totalMonths(5)
//                .totalPrice(10000)
//                .user(user)
//                .car(car)
//                .status(status)
//                .build();
//        subscriptionRequestDTO = SubscriptionRequestDTO.builder()
//                .monthPrice("2000")
//                .totalMonths("5")
//                .userId("1")
//                .carId("1")
//                .build();


    }

//    @DisplayName("addSubscription must return valid generated id when subscription is valid")
//    @Test
//    void addSubscription() {
//        Mockito.when(subscriptionDAO.addSubscription(any())).thenReturn(Long.valueOf(subscription.getId()));
//        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.ofNullable(user));
//        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.ofNullable(car));
//        Mockito.when(managerDAO.getManager(manager.getId())).thenReturn(Optional.ofNullable(manager));
//
//        long actual = subscriptionService.addSubscription(subscriptionRequestDTO);
//        Mockito.verify(subscriptionDAO, Mockito.times(1)).addSubscription(any());
//        assertEquals(1, actual);
//    }

//    @DisplayName("addSubscription must throw ServerException when subscription is not valid")
//    @Test
//    void addSubscriptionMustThrowServerException() {
//        Mockito.when(subscriptionDAO.addSubscription(any())).thenReturn(Long.valueOf(0));
//        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.ofNullable(user));
//        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.ofNullable(car));
//        Mockito.when(managerDAO.getManager(manager.getId())).thenReturn(Optional.ofNullable(manager));
//
//        assertThatThrownBy(() -> subscriptionService.addSubscription(subscriptionRequestDTO))
//                .hasMessage("Subscription not added");
//        Mockito.verify(subscriptionDAO, Mockito.times(1)).addSubscription(any());
//    }

    @DisplayName("getSubscription must return valid subscription when id is valid")
    @Test
    void getSubscription() {
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        SubscriptionDTO actual = subscriptionService.getSubscription(subscription.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).getSubscription(subscription.getId());
        assertAll(() -> {
            assertEquals(subscription.getId(), actual.id());
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

//    @DisplayName("updateSubscription checks if the carDao method is called")
//    @Test
//    void updateSubscription() {
//        subscription.setCreateDate("2112");
//        Mockito.doNothing().when(subscriptionDAO).updateSubscription(any());
//        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
//        subscriptionService.updateSubscription(subscriptionRequestDTO, subscription.getId());
//        Mockito.verify(subscriptionDAO, Mockito.times(1)).updateSubscription(any());
//    }

    @DisplayName("deleteSubscription checks if the carDao method is called")
    @Test
    void deleteSubscription() {
        Mockito.doNothing().when(subscriptionDAO).deleteSubscription(subscription.getId());
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        subscriptionService.deleteSubscription(subscription.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).deleteSubscription(subscription.getId());
    }
}