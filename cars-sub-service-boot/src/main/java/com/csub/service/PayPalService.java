package com.csub.service;

import com.csub.controller.request.PayPalRequestDTO;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PayPalService {

    Payment createPayment(
            PayPalRequestDTO paymentPayPal,
            String successUrl,
            String cancelUrl
    ) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    void updateTotalPriseSubscription(long id, int price);
}
