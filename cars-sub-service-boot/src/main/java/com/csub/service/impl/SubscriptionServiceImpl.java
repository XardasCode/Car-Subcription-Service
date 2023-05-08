package com.csub.service.impl;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.dao.CarDAO;
import com.csub.dao.ManagerDAO;
import com.csub.dao.SubscriptionDAO;
import com.csub.dao.UserDAO;
import com.csub.dto.SubscriptionDTO;
import com.csub.dto.mapper.SubscriptionDTOMapper;
import com.csub.entity.*;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.SubscriptionService;
import com.csub.util.SubscriptionSearchInfo;
import com.csub.util.SubscriptionStatusList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDAO subscriptionDAO;

    private final CarDAO carDAO;

    private final UserDAO userDAO;

    private final ManagerDAO managerDAO;

    private final SubscriptionDTOMapper subscriptionDTOMapper;

    @Override
    @Transactional
    public long addSubscription(SubscriptionRequestDTO subscription) {
        log.debug("Adding subscription: {}", subscription);

        Subscription subscriptionEntity = Subscription.createSubscriptionFromRequest(subscription);
        User user = userDAO.getUser(Long.parseLong(subscription.getUserId())).orElseThrow(() -> new ServerException("User not found", ErrorList.USER_NOT_FOUND));
        Car car = carDAO.getCar(Long.parseLong(subscription.getCarId())).orElseThrow(() -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));
        Manager manager = managerDAO.getManager(Long.parseLong(subscription.getManagerId())).orElseThrow(() -> new ServerException("Manager not found", ErrorList.MANAGER_NOT_FOUND));
        SubscriptionStatus status = getSubscriptionStatus(SubscriptionStatusList.UNDER_CONSIDERATION.getStatusId());


        subscriptionEntity.setStatus(status);
        subscriptionEntity.getStatus().getSubscriptions().add(subscriptionEntity);

        subscriptionEntity.setUser(user);
        subscriptionEntity.getUser().setSubscription(subscriptionEntity);

        subscriptionEntity.setManager(manager);
        subscriptionEntity.getManager().getSubscriptions().add(subscriptionEntity);

        subscriptionEntity.setCar(car);
        subscriptionEntity.getCar().setSubscription(subscriptionEntity);


        long id = subscriptionDAO.addSubscription(subscriptionEntity).orElseThrow(
                () -> new ServerException("Subscription not added", ErrorList.SUBSCRIPTION_ALREADY_EXISTS));
        log.debug("Subscription added: {}", subscription);
        return id;
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
    public void confirmSubscription(long id) {
        log.debug("Confirming subscription with id {}", id);
        Subscription subscription = getSubscriptionEntity(id);
        subscription.setActive(true);
        SubscriptionStatus status = getSubscriptionStatus(SubscriptionStatusList.CONFIRM_STATUS.getStatusId());
        subscription.setStatus(status);
        subscriptionDAO.updateSubscription(subscription);
    }

    @Override
    @Transactional
    public void rejectSubscription(long id) {
        log.debug("Rejecting subscription with id {}", id);
        Subscription subscription = getSubscriptionEntity(id);
        subscription.setActive(false);
        SubscriptionStatus status = getSubscriptionStatus(SubscriptionStatusList.REJECT_STATUS.getStatusId());
        subscription.setStatus(status);
        subscriptionDAO.updateSubscription(subscription);
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
}
