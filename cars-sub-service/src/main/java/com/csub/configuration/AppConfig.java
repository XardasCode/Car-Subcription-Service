package com.csub.configuration;

import com.csub.repository.dao.CarDAO;
import com.csub.repository.dao.ManagerDAO;
import com.csub.repository.dao.SubscriptionDAO;
import com.csub.repository.dao.UserDAO;
import com.csub.repository.dao.postgre.PostgreCarDAO;
import com.csub.repository.dao.postgre.PostgreManagerDAO;
import com.csub.repository.dao.postgre.PostgreSubscriptionDAO;
import com.csub.repository.dao.postgre.PostgreUserDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
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
        log.info("Configuring main database data source");

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(properties.getProperty("app.db.driver"));
        dataSource.setUrl(properties.getProperty("app.db.url"));
        dataSource.setUsername(properties.getProperty("app.db.username"));
        dataSource.setPassword(properties.getProperty("app.db.pass"));
        return dataSource;
    }

    @Bean
    public org.apache.logging.log4j.core.LoggerContext loggerContext() throws IOException {
        return org.apache.logging.log4j.core.config.Configurator.initialize(null,
                ConfigurationSource.fromUri(new ClassPathResource("log4j2.xml").getURI()));
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.csub.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.dialect", properties.getProperty("hibernate.dialect"));
        prop.put("hibernate.connection.driver_class", properties.getProperty("hibernate.connection.driver_class"));
        prop.put("hibernate.connection.url", properties.getProperty("hibernate.connection.url"));
        prop.put("hibernate.connection.username", properties.getProperty("hibernate.connection.username"));
        prop.put("hibernate.connection.password", properties.getProperty("hibernate.connection.password"));
        prop.put("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
        prop.put("hibernate.hbm2ddl", properties.getProperty("hibernate.hbm2ddl"));

        return prop;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private static final String DB_TYPE = "app.db.type";
    private static final String DB_ERROR = "Unsupported database type";
    private static final String POSTGRE = "postgre";

    @Bean
    public UserDAO userDAO() {
        if (Objects.equals(properties.getProperty(DB_TYPE), POSTGRE)) {
            return new PostgreUserDAO(sessionFactory().getObject());
        } else {
            throw new RuntimeException(DB_ERROR);
        }
    }

    @Bean
    public CarDAO carDAO() {
        if (Objects.equals(properties.getProperty(DB_TYPE), POSTGRE)) {
            return new PostgreCarDAO(sessionFactory().getObject());
        } else {
            throw new RuntimeException(DB_ERROR);
        }
    }

    @Bean
    public SubscriptionDAO subscriptionDAO() {
        if (Objects.equals(properties.getProperty(DB_TYPE), POSTGRE)) {
            return new PostgreSubscriptionDAO(sessionFactory().getObject());
        } else {
            throw new RuntimeException(DB_ERROR);
        }
    }

    @Bean
    public ManagerDAO managerDAO() {
        if (Objects.equals(properties.getProperty(DB_TYPE), POSTGRE)) {
            return new PostgreManagerDAO(sessionFactory().getObject());
        } else {
            throw new RuntimeException(DB_ERROR);
        }
    }

    // This is for H2 database
    // We will use it for testing
//    @Bean
//    public DataSource embeddedDataSource() {
//        log.info("Configuring embedded data source (H2)");
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("classpath:jdbc/schema.sql")
//                .addScript("classpath:jdbc/test-data.sql").build();
//    }

}
