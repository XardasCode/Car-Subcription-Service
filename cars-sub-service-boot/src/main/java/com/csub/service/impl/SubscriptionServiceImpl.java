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
    private static final String CONFIRM_STATUS = "4";
    private static final String REJECT_STATUS = "5";


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
        Optional<User> user = userDAO.getUser(Long.parseLong(subscription.getUser_id()));
        Optional<Car> car = carDAO.getCar(Long.parseLong(subscription.getCar_id()));
        Optional<Manager> manager = managerDAO.getManager(Long.parseLong(subscription.getManager_id()));

//        subscriptionEntity.setStatus(status.get());
        subscriptionEntity.getStatus().getSubscriptions().add(subscriptionEntity);

        subscriptionEntity.setUser(user.get());
        subscriptionEntity.getUser().setSubscription(subscriptionEntity);

        subscriptionEntity.setManager(manager.get());
        subscriptionEntity.getManager().getSubscriptions().add(subscriptionEntity);

        subscriptionEntity.setCar(car.get());
        subscriptionEntity.getCar().setSubscription(subscriptionEntity);


        long id = subscriptionDAO.addSubscription(subscriptionEntity);
        if (id == 0) {
            log.warn("Subscription not added");
            throw new ServerException("Subscription not added", ErrorList.SUBSCRIPTION_ALREADY_EXISTS);
        }
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
    public void updateSubscription(SubscriptionRequestDTO subscription, long id) {
        log.debug("Updating subscription: {}", subscription);
        Subscription dbSub = getSubEntity(id);
        Subscription subscriptionEntity = Subscription.createSubscriptionFromRequest(subscription);
        updateSubEntity(subscription, subscriptionEntity, dbSub);
        Subscription.mergeSubscription(dbSub, subscriptionEntity);
        subscriptionDAO.updateSubscription(subscriptionEntity);
        log.debug("Subscription updated: {}", subscription);
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

    private User getUserById(String userId) {
        log.debug("Getting sub user by id {}", userId);
        return userDAO.getUser(Long.parseLong(userId))
                .orElseThrow(() -> new ServerException("User status not found", ErrorList.USER_NOT_FOUND));
    }

    private Car getCarById(String carId) {
        log.debug("Getting sub status by id {}", carId);
        return carDAO.getCar(Long.parseLong(carId))
                .orElseThrow(() -> new ServerException("Car not found", ErrorList.CAR_NOT_FOUND));
    }

    private Manager getManagerById(String managerId) {
        log.debug("Getting manager by id {}", managerId);
        return managerDAO.getManager(Long.parseLong(managerId))
                .orElseThrow(() -> new ServerException("Manager not found", ErrorList.MANAGER_NOT_FOUND));
    }

    private Subscription getSubEntity(long id) {
        log.debug("Getting subscription with id {}", id);
        return subscriptionDAO.getSubscription(id)
                .orElseThrow(() -> new ServerException("Subscription not found", ErrorList.SUBSCRIPTION_NOT_FOUND));
    }


    private void updateSubEntity(SubscriptionRequestDTO subscription, Subscription subscriptionEntity, Subscription dbSub) {

        if (subscription.getStatus_id() != null) {
            long statusId = Long.parseLong(subscription.getStatus_id());
//            subscriptionEntity.setStatus(status);
            if (statusId != dbSub.getStatus().getId()) {
                subscriptionEntity.getStatus().getSubscriptions().add(subscriptionEntity);
            }
        } else {
            subscriptionEntity.setStatus(dbSub.getStatus());
        }

        if (subscription.getUser_id() != null) {
            long userId = Long.parseLong(subscription.getStatus_id());
            User user = getUserById(subscription.getUser_id());
            subscriptionEntity.setUser(user);

        } else {
            subscriptionEntity.setUser(dbSub.getUser());
        }

        if (subscription.getCar_id() != null) {
            long carId = Long.parseLong(subscription.getCar_id());
            Car car = getCarById(subscription.getCar_id());
            subscriptionEntity.setCar(car);

        } else {
            subscriptionEntity.setCar(dbSub.getCar());
        }

        if (subscription.getManager_id() != null) {
            long managerId = Long.parseLong(subscription.getManager_id());
            Manager manager = getManagerById(subscription.getManager_id());
            subscriptionEntity.setManager(manager);
            if (managerId != dbSub.getManager().getId()) {
                subscriptionEntity.getManager().getSubscriptions().add(subscriptionEntity);
            }
        } else {
            subscriptionEntity.setManager(dbSub.getManager());
        }

    }

    @Override
    @Transactional
    public void confirmSubscription(long id) {
        log.debug("Confirming subscription with id {}", id);
        Subscription subscription = getSubEntity(id);
        subscription.setActive(true);
//        subscription.setStatus(status);
        subscriptionDAO.updateSubscription(subscription);
    }

    @Override
    @Transactional
    public void rejectSubscription(long id) {
        log.debug("Rejecting subscription with id {}", id);
        Subscription subscription = getSubEntity(id);
        subscription.setActive(false);
//        subscription.setStatus(status);
        subscriptionDAO.updateSubscription(subscription);
    }
}
