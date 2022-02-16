package com.zvenova.like_my.base;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile({ "testWithDB" })
@Configuration
@EnableJpaRepositories("com.zvenova.like_my.repository")
@EnableTransactionManagement
public class DatabaseTestConfig {

    @Primary
    @Bean
    public DataSource dataSource() {

        TestContainersInit containersInitializer = TestContainersInit.getInstance();

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername(containersInitializer.username);
        dataSource.setPassword(containersInitializer.password);
        dataSource.setJdbcUrl(containersInitializer.url);
        dataSource.setAutoCommit(false);
        dataSource.setMaximumPoolSize(1);
        dataSource.setConnectionTimeout(1000);
        dataSource.setLeakDetectionThreshold(1000);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setDataSource(dataSource);
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPackagesToScan("com.zvenova.like_my");
        factoryBean.setPersistenceUnitName("like_my");
        factoryBean.setJpaProperties(jpaHibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    protected Properties jpaHibernateProperties() {

        final Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("hibernate.type", "info");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.jdbc.batch_size", "20");
        properties.setProperty("hibernate.order_inserts", "true");
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");

        return properties;
    }
}
