package com.csub.service;

import com.csub.controller.request.PayPalRequestDTO;

public interface PayPalService {

    String createPayment(
            PayPalRequestDTO paymentPayPal,
            String successUrl,
            String cancelUrl
    );

    boolean executePayment(String paymentId, String payerId, long id);

}
