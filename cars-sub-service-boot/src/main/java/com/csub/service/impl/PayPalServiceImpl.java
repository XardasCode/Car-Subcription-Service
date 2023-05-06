package com.csub.service.impl;

import com.csub.controller.request.PayPalRequestDTO;
import com.csub.dao.SubscriptionDAO;
import com.csub.entity.Subscription;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.PayPalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayPalServiceImpl implements PayPalService {

    @Autowired
    private  APIContext apiContext;

    private final SubscriptionDAO subscriptionDAO;


    @Override
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

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        log.info("Executing payment");
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        log.info("Payment executed");
        return payment.execute(apiContext,paymentExecution);
    }

    @Override
    @Transactional
    public void updateTotalPriseSubscription(long id, int price) {
        log.debug("Updating totalPrise in subscription");
        Subscription subscription = getSubEntity(id);
        subscription.setTotalPrice(subscription.getTotalPrice()-price);
        subscriptionDAO.updateSubscription(subscription);
        log.debug("TotalPrise in subscription updated");
    }
    private Subscription getSubEntity(long id){
        log.debug("Getting subscription with id {}", id);
        return subscriptionDAO.getSubscription(id)
                .orElseThrow(() -> new ServerException("Subscription not found", ErrorList.SUBSCRIPTION_NOT_FOUND));
    }

}
