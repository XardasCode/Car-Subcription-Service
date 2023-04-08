package com.csub.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.csub")
@EnableWebMvc
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@Slf4j
public class AppConfig {
    @Autowired
    Environment properties;


    @Bean
    public DataSource dataSource() {
        log.debug("Configuring main database data source");

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(properties.getProperty("app.db.driver"));
        dataSource.setUrl(properties.getProperty("app.db.url"));
        dataSource.setUsername(properties.getProperty("app.db.username"));
        dataSource.setPassword(properties.getProperty("app.db.pass"));
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
        prop.put("hibernate.dialect", properties.getProperty("hibernate.dialect"));
        prop.put("hibernate.connection.driver_class", properties.getProperty("hibernate.connection.driver_class"));
        prop.put("hibernate.connection.url", properties.getProperty("hibernate.connection.url"));
        prop.put("hibernate.connection.username", properties.getProperty("hibernate.connection.username"));
        prop.put("hibernate.connection.password", properties.getProperty("hibernate.connection.password"));
        prop.put("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
        prop.put("hibernate.hbm2ddl", properties.getProperty("hibernate.hbm2ddl"));
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
