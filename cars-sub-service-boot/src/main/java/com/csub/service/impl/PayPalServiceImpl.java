package com.csub.service.impl;

import com.csub.controller.request.PayPalRequestDTO;
import com.csub.dao.SubscriptionDAO;
import com.csub.entity.Subscription;
import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.csub.service.PayPalService;
import com.csub.util.PayPalPaymentManager;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PayPalServiceImpl implements PayPalService {

    private final PayPalPaymentManager payPalPaymentManager;
    private final SubscriptionDAO subscriptionDAO;


    @Override
    @Transactional
    public String createPayment( PayPalRequestDTO paymentPayPal, String successUrl, String cancelUrl) {
        log.info("Creating payment: {}", paymentPayPal);

        try {
            Payment payment = payPalPaymentManager.createPayment(paymentPayPal, successUrl, cancelUrl);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    log.info("Approving payment");
                    return link.getHref();
                }
            }
            log.info("Payment failed");
            throw new ServerException("Failed to create payment", ErrorList.PAYMENT_FAILED);

        } catch (PayPalRESTException e) {
            throw new  ServerException("Failed to create payment",e, ErrorList.PAYMENT_FAILED);
        }

    }

    @Override
    @Transactional
    public boolean executePayment(String paymentId, String payerId, long id){
        log.info("Executing payment");
        try {
            Payment payment = payPalPaymentManager.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {

                log.info("Payment executed {}", payment.toJSON());
                String total = payment.getTransactions().get(0).getAmount().getTotal();
                int price = (int)  Double.parseDouble(total);

                log.debug("Updating totalPrise in subscription. price = {}", price);
                updateTotalPriseSubscription(id, price);
                log.debug("TotalPrise in subscription updated");

            }else{
                return false;
            }
        } catch (PayPalRESTException e) {
            log.info("Payment executing failed");
            return false;
        }
        log.info("Payment executed");
        return true;
    }


    private void updateTotalPriseSubscription(long id, int price) {
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
