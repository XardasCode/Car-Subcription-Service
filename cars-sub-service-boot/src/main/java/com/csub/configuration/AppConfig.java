package com.csub.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.csub")
@ConfigurationProperties(prefix = "app")
@Slf4j
@EnableWebMvc
@Getter
@Setter
public class AppConfig {
    @Value("${app.db.driver}")
    private String dbDriver;

    @Value("${app.db.url}")
    private String dbUrl;

    @Value("${app.db.username}")
    private String dbUsername;

    @Value("${app.db.pass}")
    private String dbPassword;

    @Value("${app.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${app.hibernate.show_sql}")
    private String hibernateShowSql;


    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.csub");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean
    public DataSource dataSource() {
        log.debug("Configuring main database data source");

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        log.debug("Main database data source configured");
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        log.debug("Configuration session factory bean");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.csub.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        log.debug("Session factory bean configured");
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        log.debug("Configuring hibernate properties");
        Properties prop = new Properties();
        prop.put("hibernate.dialect", hibernateDialect);
        prop.put("hibernate.connection.driver_class", dbDriver);
        prop.put("hibernate.connection.url", dbUrl);
        prop.put("hibernate.connection.username", dbUsername);
        prop.put("hibernate.connection.password", dbPassword);
        prop.put("hibernate.show_sql", hibernateShowSql);
        log.debug("Hibernate properties configured");
        return prop;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        log.debug("Configuring transaction manager");
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        log.debug("Transaction manager configured");
        return transactionManager;
    }
}
