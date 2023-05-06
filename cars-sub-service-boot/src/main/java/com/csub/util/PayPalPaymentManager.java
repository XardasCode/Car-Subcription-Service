package com.csub.util;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalPaymentManager { // Все звідси потрібно перенести у AppConfig і видалити цей клас

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @Bean // Біни мають оголошуватись в основному конфігураційному класі AppConfig
    public Map<String,String> paypalConfig(){
        Map<String,String> configMap = new HashMap<>();
        configMap.put("mode",mode);
        return configMap;

    }
    @Bean
    public OAuthTokenCredential qAuthTokenCredential(){
        return new OAuthTokenCredential(clientId,clientSecret,paypalConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(qAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalConfig());
        return context;
    }

}
