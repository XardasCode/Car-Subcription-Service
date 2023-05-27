package com.csub.service.impl;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dao.SubscriptionDAO;
import com.csub.dao.UserDAO;
import com.csub.dto.CarDTO;
import com.csub.dto.SubscriptionDTO;
import com.csub.dto.mapper.SubscriptionDTOMapper;
import com.csub.entity.*;
import com.csub.util.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    @Mock
    private SubscriptionDAO subscriptionDAO;
    @Mock
    private CarDAO carDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private GenerateReportPDF generateReportPDF;

    @Mock
    private EmailSender emailSender;

    User user;
    Subscription subscription;
    Car car;
    User manager;
    CarStatus carStatus;
    SubscriptionStatus subscriptionStatus;
    UserRole userRole;
    SubscriptionRequestDTO subscriptionRequestDTO;

    @BeforeEach
    void setUp() {
        subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, carDAO, userDAO, new SubscriptionDTOMapper(), generateReportPDF, emailSender);
        userRole = UserRole.builder().name("USER").build();
        user = User.builder()
                .id(0)
                .name("Fogell")
                .surname("McLovin")
                .email("test@gmail.com")
                .password("DTlpxUE8OnvOh96bMNDYDHIdwTes0oLFP6pzA5cZSB7SGB9jU+eWJmeKNRUK0Np6")
                .phone("0985491837")
                .isVerified(false)
                .isBlocked(false)
                .verificationCode(null)
                .role(userRole)
                .build();

        userRole = UserRole.builder().name("MANAGER").build();
        manager = User.builder()
                .id(0)
                .name("John")
                .surname("Week")
                .email("test@gmail.com")
                .password("DTlpxUE8OnvOh96bMNDYDHIdwTes0oLFP6pzA5cZSB7SGB9jU+eWJmeKNRUK0Np6")
                .phone("0985491839")
                .isVerified(false)
                .isBlocked(false)
                .verificationCode(null)
                .role(userRole)
                .build();

        carStatus = CarStatus.builder().id(1).name("AVAILABLE").cars(new HashSet<>()).build();

        car = Car.builder()
                .id(0)
                .name("Camry")
                .model("3.5 6AT Premium")
                .brand("Toyota ")
                .year(2019)
                .color("Black")
                .price(2250)
                .fuelType("Gasoline")
                .chassisNumber("9591")
                .regNumber("TT")
                .regDate("2022-10-05")
                .mileage(11230)
                .lastServiceDate("2022-10-05")
                .carStatus(carStatus)
                .build();
        // SubStatues: UNDER_CONSIDERATION CONFIRM_STATUS
        subscriptionStatus = SubscriptionStatus.builder().id(1).name("UNDER_CONSIDERATION").subscriptions(new HashSet<>()).build();

        subscription = Subscription.builder()
                .id(0)
                .isActive(false)
                .monthPrice(2000)
                .totalMonths(5)
                .passportNumber("1234567890")
                .ipnNumber("10101010-10101")
                .socMediaLink("@McLovin")
                .lastPayDate(null)
                .user(user)
                .car(car)
                .manager(manager)
                .status(subscriptionStatus)
                .build();

        subscriptionRequestDTO = SubscriptionRequestDTO.builder()
                .monthPrice(String.valueOf(subscription.getMonthPrice()))
                .totalMonths(String.valueOf(subscription.getTotalMonths()))
                .userId(String.valueOf(subscription.getUser().getId()))
                .carId(String.valueOf((subscription.getCar().getId())))
                .passportNumber(subscription.getPassportNumber())
                .ipnNumber(subscription.getIpnNumber())
                .socMediaLink(subscription.getSocMediaLink())
                .build();


    }

    @DisplayName("addSubscription must return valid generated id when subscription is valid")
    @Test
    void addSubscription() {

        Mockito.when(subscriptionDAO.addSubscription(subscription)).thenReturn(OptionalLong.of(subscription.getId()));
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.ofNullable(user));
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.ofNullable(car));
        Mockito.when(carDAO.getCarStatusById(CarStatusList.UNAVAILABLE.getStatusId())).thenReturn(Optional.ofNullable(carStatus));
        Mockito.when(subscriptionDAO.getSubscriptionStatus(SubscriptionStatusList.UNDER_CONSIDERATION.getStatusId())).thenReturn(Optional.ofNullable(subscriptionStatus));

        long actual = subscriptionService.addSubscription(subscriptionRequestDTO);
        Mockito.verify(subscriptionDAO, Mockito.times(1)).addSubscription(subscription);
        assertEquals(0, actual);
    }

    @DisplayName("addSubscription must throw ServerException when subscription is not valid")
    @Test
    void addSubscriptionMustThrowServerException() {
        Mockito.when(subscriptionDAO.addSubscription(subscription)).thenReturn(OptionalLong.empty());
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(Optional.ofNullable(user));
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.ofNullable(car));
        Mockito.when(carDAO.getCarStatusById(CarStatusList.UNAVAILABLE.getStatusId())).thenReturn(Optional.ofNullable(carStatus));
        Mockito.when(subscriptionDAO.getSubscriptionStatus(SubscriptionStatusList.UNDER_CONSIDERATION.getStatusId())).thenReturn(Optional.ofNullable(subscriptionStatus));


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


    @DisplayName("deleteSubscription checks if the subscriptionDAO method is called")
    @Test
    void deleteSubscription() {
        Mockito.doNothing().when(subscriptionDAO).deleteSubscription(subscription.getId());
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        subscriptionService.deleteSubscription(subscription.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).deleteSubscription(subscription.getId());
    }

    @DisplayName("getSubscription must return list of subscription ")
    @Test
    void getAllSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);
        Mockito.when(subscriptionDAO.getAllSubscription()).thenReturn(subscriptions);
        List<SubscriptionDTO> actual = subscriptionService.getAllSubscriptions();
        Mockito.verify(subscriptionDAO, Mockito.times(1)).getAllSubscription();
        assertAll(() -> {
            assertEquals(subscription.getId(), actual.get(0).id());
            assertEquals(subscription.getIpnNumber(), actual.get(0).ipnNumber());
        });
    }

    @DisplayName("getPageCount checks if the subscriptionDAO method getCarsCount is called")
    @Test
    void getPageCount() {
        int size = 12;
        List<String> filter = new ArrayList<>();
        filter.add("isActive:true");
        Mockito.when(subscriptionDAO.getSubscriptionsCount(size, filter)).thenReturn(24);
        int actual = subscriptionService.getPageCount(size, filter);
        Mockito.verify(subscriptionDAO, Mockito.times(1)).getSubscriptionsCount(size, filter);
        assertEquals(2, actual);
    }

    @DisplayName("confirmSubscription checks if the subscriptionDAO method is called")
    @Test
    void confirmSubscription() {

        Mockito.doNothing().when(subscriptionDAO).updateSubscription(subscription);
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        Mockito.when(userDAO.getUser(manager.getId())).thenReturn(Optional.of(manager));
        Mockito.when(subscriptionDAO.getSubscriptionStatus(SubscriptionStatusList.CONFIRM_STATUS.getStatusId())).thenReturn(Optional.ofNullable(subscriptionStatus));
        Mockito.doNothing().when(emailSender).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());
        subscriptionService.confirmSubscription(subscription.getId(), manager.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).updateSubscription(subscription);
    }

    @DisplayName("rejectSubscription checks if the subscriptionDAO method is called")
    @Test
    void rejectSubscription() {
        subscription.setCar(car);
        Mockito.doNothing().when(subscriptionDAO).deleteSubscription(subscription.getId());
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.ofNullable(car));
        Mockito.when(carDAO.getCarStatusById(CarStatusList.AVAILABLE.getStatusId())).thenReturn(Optional.ofNullable(carStatus));
        Mockito.doNothing().when(emailSender).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());
        subscriptionService.rejectSubscription(subscription.getId());
        Mockito.verify(subscriptionDAO, Mockito.times(1)).deleteSubscription(subscription.getId());
    }

    @DisplayName("searchSubscription must return filtered list of subscription ")
    @Test
    void searchSubscription() {
        SubscriptionSearchInfo subscriptionSearchInfo = SubscriptionSearchInfo.builder().
                filter(new ArrayList<>())
                .sortField("IsActive")
                .direction("ASC")
                .page(1)
                .size(12)
                .build();
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);

        Mockito.when(subscriptionDAO.searchSubscription(subscriptionSearchInfo)).thenReturn(subscriptions);
        List<SubscriptionDTO> actual = subscriptionService.searchSubscription(subscriptionSearchInfo);
        Mockito.verify(subscriptionDAO, Mockito.times(1)).searchSubscription(subscriptionSearchInfo);
        assertAll(() -> {
            assertEquals(subscription.getId(), actual.get(0).id());
            assertEquals(subscription.getIpnNumber(), actual.get(0).ipnNumber());
        });
    }

    @SneakyThrows
    @DisplayName("getReportPDF checks if the getReportPDF method is called")
    @Test
    void getReportPDF() {

        byte[] pdf = new byte[1];
        Mockito.when(subscriptionDAO.getSubscription(subscription.getId())).thenReturn(Optional.of(subscription));
        Mockito.when(carDAO.getCar(car.getId())).thenReturn(Optional.ofNullable(car));
        OngoingStubbing<byte[]> ongoingStubbing = Mockito.when(generateReportPDF.generatePdf(car, subscription)).thenReturn(pdf);
        byte[] actual = subscriptionService.getReportPDF(subscription.getId());
        Mockito.verify(generateReportPDF, Mockito.times(1)).generatePdf(car, subscription);
        assertEquals(pdf, actual);
    }
}

