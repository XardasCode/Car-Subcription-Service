package com.csub.controller;

import com.csub.dto.SubscriptionDTO;
import com.csub.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionRestController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<SubscriptionDTO> getAllSubscriptions() {
        log.info("Getting all subscriptions");
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping(value = "/{id}")
    public SubscriptionDTO getSubscription(@PathVariable long id) {
        log.info("Getting subscription with id {}", id);
        return subscriptionService.getSubscription(id);
    }

    @GetMapping(value = "/user/{id}")
    public List<SubscriptionDTO> getSubscriptionsByUserId(@PathVariable long id) {
        log.info("Getting subscriptions by user id {}", id);
        return subscriptionService.getSubscriptionsByUserId(id);
    }
}
