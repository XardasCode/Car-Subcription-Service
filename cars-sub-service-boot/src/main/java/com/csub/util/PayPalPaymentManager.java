package com.csub.util;

import com.csub.controller.request.PayPalRequestDTO;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class PayPalPaymentManager {

    private final APIContext apiContext;
    public Payment createPayment(
                                  PayPalRequestDTO paymentPayPal,
                                  String successUrl,
                                  String cancelUrl
    ) throws PayPalRESTException {
        log.info("Creating payment: {}", paymentPayPal);

        double total = Double.parseDouble(paymentPayPal.getPrice());
        String method = paymentPayPal.getMethod();
        String currency = paymentPayPal.getCurrency();
        String intent = paymentPayPal.getIntent();
        String description = paymentPayPal.getDescription();

        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.valueOf(total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactionList);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        log.info("Payment created");
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        log.info("Executing payment");
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        log.info("Payment executed");
        return payment.execute(apiContext,paymentExecution);
    }

}
