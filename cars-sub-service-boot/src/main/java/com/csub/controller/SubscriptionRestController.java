package com.csub.controller;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.controller.util.JSONInfo;
import com.csub.dto.SubscriptionDTO;
import com.csub.service.SubscriptionService;
import com.csub.util.SubscriptionSearchInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
@CrossOrigin
public class SubscriptionRestController {
    private final SubscriptionService subscriptionService;

    private static final String SUCCESS = "success";

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
    public SubscriptionDTO getSubscriptionsByUserId(@PathVariable long id) {
        log.info("Getting subscriptions by user id {}", id);
        return subscriptionService.getSubscriptionByUserId(id);
    }

    @PostMapping
    public ResponseEntity<JSONInfo> addSubscription(@RequestBody @Valid SubscriptionRequestDTO subscription) {
        log.info("Adding subscription: {}", subscription);
        long id = subscriptionService.addSubscription(subscription);
        log.info("Subscription added");
        return new ResponseEntity<>(new JSONInfo(SUCCESS,"Subscription added"), HttpStatus.CREATED);
    }
    @PostMapping(value = "/{id}")
    public ResponseEntity<JSONInfo> updateSubscription(@RequestBody SubscriptionRequestDTO subscription, @PathVariable long id) {
        log.info("Updating subscription: {}", subscription);
        subscriptionService.updateSubscription(subscription,id);
        log.info("Subscription updated");
        return new ResponseEntity<>(new JSONInfo(SUCCESS,"Subscription updated"), HttpStatus.OK);
    }
    @GetMapping(value = "/search")
    public List<SubscriptionDTO> searchSubscription(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "") List<String> filter
    ) {
        log.info("Getting subscription");
        return subscriptionService.searchSubscription(
                SubscriptionSearchInfo.builder()
                        .page(page)
                        .size(size)
                        .sortField(sortField)
                        .direction(direction)
                        .filter(filter)
                        .build()
        );
    }
}
