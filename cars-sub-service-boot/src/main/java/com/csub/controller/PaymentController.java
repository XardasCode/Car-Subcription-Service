package com.csub.controller;

import com.csub.controller.request.PayPalRequestDTO;
import com.csub.controller.util.JSONInfo;
import com.csub.service.PayPalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/pay")
public class PaymentController {

    private final PayPalService payPalService;

    @Value("${redirect.success-payment-page}")
    private String REDIRECT_SUCCESS_PAGE;

    @Value("${redirect.error-payment-page}")
    private String REDIRECT_ERROR_PAGE;

    @Value("${redirect.approving.success}")
    private String SUCCESS_URL;

    @Value("${redirect.approving.cancel}")
    private String CANCEL_URL;


    @PostMapping(value = "/paypal/{id}")
    public ResponseEntity<JSONInfo> createPaymentPayPal(@RequestBody @Valid PayPalRequestDTO paymentPayPal, @PathVariable String id) {
        log.info("Creating payment: {}", paymentPayPal);
        String url = payPalService.createPayment(paymentPayPal,SUCCESS_URL+id,CANCEL_URL);
        return new ResponseEntity<>(JSONInfo.builder().message(url).build(),HttpStatus.OK);
    }

    @GetMapping(value = "/cancel")
    public ResponseEntity<Object> canselPay(){
        URI page =  URI.create(REDIRECT_ERROR_PAGE);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(page);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping(value = "/success/{id}")
    public ResponseEntity<Object> successApprovingPaymentPayPal(@PathVariable long id, @RequestParam("paymentId") String paymentID, @RequestParam("PayerID") String payerId){
        log.info("Executing payment");
        URI page = URI.create(payPalService.executePayment(paymentID, payerId, id) ? REDIRECT_SUCCESS_PAGE : REDIRECT_ERROR_PAGE);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(page);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}

