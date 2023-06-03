package com.csub;

import com.csub.util.PasswordManager;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.uploadcare.api.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableScheduling
public class AppConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${uploadcare.public-key}")
    private String uploadCarePublicKey;

    @Value("${uploadcare.secret-key}")
    private String uploadCareSecretKey;

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.csub");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean
    public Client client() {
        return new Client(uploadCarePublicKey, uploadCareSecretKey);
    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean
    public Map<String, String> paypalConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;

    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean
    public OAuthTokenCredential qAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalConfig());
    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(qAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalConfig());
        return context;
    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean
    public PasswordManager passwordManager() {
        PasswordManager passwordManager = new PasswordManager();
        return passwordManager;
    }
}