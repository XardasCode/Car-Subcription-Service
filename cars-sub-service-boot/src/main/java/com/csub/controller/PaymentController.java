package com.csub.controller;

import com.csub.controller.request.PayPalRequestDTO;
import com.csub.controller.util.JSONInfo;
import com.csub.service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/pay")
public class PaymentController {

    private final PayPalService payPalService;
    private static final String REDIRECT_SUCCESS_PAGE = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    private static final String REDIRECT_ERROR_PAGE = "https://www.youtube.com/watch?v=YnP94m5pwls";

    private static final String SUCCESS_URL = "http://localhost:8080/api/v1/pay/success/";
    private static final String CANSEL_URL = "http://localhost:8080/api/v1/pay/cansel";
    private static final String SUCCESS = "success";
    private static final String SERVER_ERROR = "error";

    @PostMapping(value = "/paypal/{id}")
    public ResponseEntity<JSONInfo> createPaymentPayPal(@RequestBody @Valid PayPalRequestDTO paymentPayPal,@PathVariable String id){
        log.info("Creating payment: {}", paymentPayPal);
        try {
            Payment payment = payPalService.createPayment(paymentPayPal,SUCCESS_URL+id,CANSEL_URL);
            for(Links link:payment.getLinks()){
                if(link.getRel().equals("approval_url")){
                    log.info("Approving payment");
                    return new ResponseEntity<>(new JSONInfo(SUCCESS,link.getHref()), HttpStatus.OK);
                }
            }
        }catch (PayPalRESTException e){
            return new ResponseEntity<>(new JSONInfo(SERVER_ERROR,e.getMessage()), HttpStatus.EXPECTATION_FAILED);
        }
        log.info("Creating payment failed");
        return new ResponseEntity<>(new JSONInfo(SERVER_ERROR,"Failed to create payment subscription"), HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/cansel")
    public  ResponseEntity<Object> canselPay()throws URISyntaxException{
        URI success = new URI(REDIRECT_ERROR_PAGE);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(success);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }


    @GetMapping(value = "/success/{id}")
    public ResponseEntity<Object> successPaymentPayPal(@PathVariable long id, @RequestParam("paymentId") String paymentID, @RequestParam("PayerID") String payerId)throws URISyntaxException
    {
        log.info("Executing payment");
        URI page;
        HttpHeaders httpHeaders = new HttpHeaders();
        try {

            Payment payment = payPalService.executePayment(paymentID,payerId);
            if(payment.getState().equals("approved")){

                log.info("Payment executed {}",payment.toJSON());
                String total = payment.getTransactions().get(0).getAmount().getTotal();
                double decimal = Double.parseDouble(total);
                int price = (int) decimal;

                log.debug("Updating totalPrise in subscription. price = {}", price);
                payPalService.updateTotalPriseSubscription(id,price);
                log.debug("TotalPrise in subscription updated");

                page = new URI(REDIRECT_SUCCESS_PAGE);
                httpHeaders.setLocation(page);
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
            }
        }catch (PayPalRESTException e){
            page = new URI(REDIRECT_ERROR_PAGE);
            httpHeaders.setLocation(page);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }
        page = new URI(REDIRECT_ERROR_PAGE);
        httpHeaders.setLocation(page);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }




}
