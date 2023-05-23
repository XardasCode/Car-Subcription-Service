package com.csub.controller;

import com.csub.controller.request.SubscriptionRequestDTO;
import com.csub.controller.util.JSONInfo;
import com.csub.dto.SubscriptionDTO;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.SubscriptionService;
import com.csub.util.SubscriptionSearchInfo;
import com.itextpdf.text.DocumentException;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


    @PostMapping
    public ResponseEntity<JSONInfo> addSubscription(@RequestBody @Valid SubscriptionRequestDTO subscription) {
        log.info("Adding subscription: {}", subscription);
        long id = subscriptionService.addSubscription(subscription);
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.CREATED);
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

    @PatchMapping(value = "/{id}/confirm/{managerId}")
    public ResponseEntity<JSONInfo> confirmSubscription(@PathVariable long id, @PathVariable long managerId) {
        log.info("Confirming subscription with id {}", id);
        subscriptionService.confirmSubscription(id, managerId);
        log.info("Subscription confirmed");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/reject")
    public ResponseEntity<JSONInfo> rejectSubscription(@PathVariable long id) {
        log.info("Rejecting subscription with id {}", id);
        subscriptionService.rejectSubscription(id);
        log.info("Subscription rejected");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }
    @GetMapping("/page-count")
    public int getPageCount(
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") List<String> filter
    ) {
        log.info("Getting page count");
        return subscriptionService.getPageCount(size, filter);
    }

    @GetMapping("/{id}/generate-pdf")
    public ResponseEntity<byte[]> getReportPDF(@PathVariable long id)  {
        byte[] pdfBytes = subscriptionService.getReportPDF(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);

    }
}
