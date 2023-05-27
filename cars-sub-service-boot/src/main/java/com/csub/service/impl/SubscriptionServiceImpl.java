package com.csub.service.impl;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dao.SubscriptionDAO;
import com.csub.dao.UserDAO;
import com.csub.dto.SubscriptionDTO;
import com.csub.dto.mapper.SubscriptionDTOMapper;
import com.csub.entity.*;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.SubscriptionService;
import com.csub.util.*;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDAO subscriptionDAO;

    private final CarDAO carDAO;

    private final UserDAO userDAO;

    private final SubscriptionDTOMapper subscriptionDTOMapper;

    private final GenerateReportPDF generateReportPDF;

    private final EmailSender emailSender;

    @Override
    @Transactional
    public long addSubscription(SubscriptionRequestDTO subscription) {
        log.debug("Adding subscription: {}", subscription);

        Subscription subscriptionEntity = Subscription.createSubscriptionFromRequest(subscription);
        long userId = Long.parseLong(subscription.getUserId());
        long carId = Long.parseLong(subscription.getCarId());
        User user = userDAO.getUser(userId).orElseThrow(
                () -> new ServerException("User not found", ErrorList.USER_NOT_FOUND));
        Car car = carDAO.getCar(carId).orElseThrow(
                () -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));

        checkIfCarAlreadySubscribed(car);
        checkIfUserAlreadySubscribed(user);

        SubscriptionStatus status = getSubscriptionStatus(SubscriptionStatusList.UNDER_CONSIDERATION.getStatusId());

        subscriptionEntity.setStatus(status);
        subscriptionEntity.getStatus().getSubscriptions().add(subscriptionEntity);

        subscriptionEntity.setUser(user);
        subscriptionEntity.getUser().setSubscription(subscriptionEntity);

        subscriptionEntity.setCar(car);
        subscriptionEntity.getCar().setSubscription(subscriptionEntity);
        CarStatus carStatus = carDAO.getCarStatusById(CarStatusList.UNAVAILABLE.getStatusId()).orElseThrow(
                () -> new ServerException("Car status not found", ErrorList.CAR_STATUS_NOT_FOUND));
        subscriptionEntity.getCar().setCarStatus(carStatus);

        return subscriptionDAO.addSubscription(subscriptionEntity).orElseThrow(
                () -> new ServerException("Subscription not added", ErrorList.SUBSCRIPTION_ALREADY_EXISTS));
    }

    private void checkIfUserAlreadySubscribed(User user) {
        if (user.getSubscription() != null) {
            throw new ServerException("User already subscribed", ErrorList.USER_ALREADY_SUBSCRIBED);
        }
    }

    private void checkIfCarAlreadySubscribed(Car car) {
        if (car.getSubscription() != null) {
            throw new ServerException("Car already subscribed", ErrorList.CAR_ALREADY_SUBSCRIBED);
        }
    }

    @Override
    @Transactional
    public SubscriptionDTO getSubscription(long id) {
        log.debug("Getting subscription with id {}", id);
        Optional<Subscription> subscription = subscriptionDAO.getSubscription((int) id);
        return subscription.map(subscriptionDTOMapper)
                .orElseThrow(() -> new ServerException("Subscription not found", ErrorList.SUBSCRIPTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteSubscription(long id) {
        log.debug("Deleting subscription with id {}", id);
        getSubscription(id);
        subscriptionDAO.deleteSubscription(id);
        log.debug("Subscription with id {} deleted", id);
    }

    @Override
    @Transactional
    public List<SubscriptionDTO> getAllSubscriptions() {
        log.debug("Getting all subscriptions");
        List<Subscription> subscriptions = subscriptionDAO.getAllSubscription();
        log.debug("Subscriptions found: {}", subscriptions.size());
        return subscriptions.stream().map(subscriptionDTOMapper).toList();
    }

    @Override
    @Transactional
    public List<SubscriptionDTO> searchSubscription(SubscriptionSearchInfo info) {
        log.debug("Getting all subscriptions");
        List<Subscription> subscriptions = subscriptionDAO.searchSubscription(info);
        log.debug("Subscriptions found: {}", subscriptions.size());
        return subscriptions.stream().map(subscriptionDTOMapper).toList();
    }

    @Override
    @Transactional
    public void confirmSubscription(long id, long managerId) {
        log.debug("Confirming subscription with id {}", id);
        Subscription subscription = getSubscriptionEntity(id);
        subscription.setActive(true);
        User manager = userDAO.getUser(managerId).orElseThrow(() -> new ServerException("Manager not found", ErrorList.MANAGER_NOT_FOUND));
        subscription.setManager(manager);
        SubscriptionStatus status = getSubscriptionStatus(SubscriptionStatusList.CONFIRM_STATUS.getStatusId());
        subscription.setStatus(status);

        emailSender.sendEmail(subscription.getUser().getEmail(), "Підписка підтверджена", "Ваша підписка була підтверджена, ви можете взяти автомобіль");
        subscriptionDAO.updateSubscription(subscription);
    }

    @Override
    @Transactional
    public void rejectSubscription(long id) {
        log.debug("Rejecting subscription with id {}", id);
        Subscription subscription = getSubscriptionEntity(id);
        Car car = carDAO.getCar(subscription.getCar().getId()).orElseThrow(() -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));
        CarStatus carStatus = carDAO.getCarStatusById(CarStatusList.AVAILABLE.getStatusId()).orElseThrow(
                () -> new ServerException("Car status not found", ErrorList.CAR_STATUS_NOT_FOUND));
        car.setCarStatus(carStatus);

        emailSender.sendEmail(subscription.getUser().getEmail(), "Підписка відхилена", "Ваша підписка була відхилена, будь ласка виправте інформацію і спробуйте ще раз");

        carDAO.updateCar(car);
        subscriptionDAO.deleteSubscription(id);
    }

    private Subscription getSubscriptionEntity(long id) {
        log.debug("Getting subscription with id {}", id);
        return subscriptionDAO.getSubscription(id)
                .orElseThrow(() -> new ServerException("Subscription not found", ErrorList.SUBSCRIPTION_NOT_FOUND));
    }

    private SubscriptionStatus getSubscriptionStatus(int statusId) {
        return subscriptionDAO.getSubscriptionStatus(statusId).orElseThrow(
                () -> new ServerException("Subscription status not found", ErrorList.SUBSCRIPTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public int getPageCount(int size, List<String> filter) {
        log.debug("Getting page count");
        int count = subscriptionDAO.getSubscriptionsCount(size, filter);
        log.debug("Count: {}", count);
        return (int) Math.ceil((double) count / size);
    }

    @Override
    @Transactional
    public byte[] getReportPDF(long id) {
        log.debug("Generating report pdf");
        Subscription subscription = subscriptionDAO.getSubscription(id).orElseThrow(
                () -> new ServerException("Subscription not found", ErrorList.SUBSCRIPTION_NOT_FOUND));
        Car car = carDAO.getCar(subscription.getCar().getId()).orElseThrow(
                () -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));

        byte[] reportPDF;
        try {
            reportPDF = generateReportPDF.generatePdf(car, subscription);
        } catch (DocumentException | IOException e) {
            throw new ServerException("Failed to generate report", e, ErrorList.GENERATING_REPORT_FAILED);
        }

        log.debug("Report generated");
        return reportPDF;
    }

}
