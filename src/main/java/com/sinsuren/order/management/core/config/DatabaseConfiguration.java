package com.sinsuren.order.management.core.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by surender.s on 05/10/17.
 */
@Configuration
@PropertySource("database.properties")
@EnableTransactionManagement
public class DatabaseConfiguration {
    @Value("${spring.datasource.driverClassName}")
    String driverClass;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.url}")
    String dbUrl;

    @Value("${spring.datasource.validationQuery}")
    String validationQuery;


    @Value("${spring.jpa.show-sql:true}")
    String jpaShowSql;

    @Value("${spring.jpa.hibernate.ddl-auto:update}")
    String jpaDdlAuto;

    @Value("${spring.jpa.database-platform}")
    String jpaDialect;

    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    String sessionContext;

    @Value("${spring.jpa.hibernate.naming-strategy}")
    String namingConvention;

    @Value("${order-management.core.db.package-to-scan}")
    String[] packageToScan;

    @Value("${order-management.core.db.persistent-unit}")
    String persistentUnit;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaDialect(new HibernateJpaDialect());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPersistenceUnitName(persistentUnit);
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.setPackagesToScan(packageToScan);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClass);
        hikariConfig.setAutoCommit(false);
        hikariConfig.setPassword(password);
        hikariConfig.setUsername(userName);
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setConnectionTestQuery(validationQuery);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        return hikariDataSource;
    }

    public Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.show_sql", jpaShowSql);
        jpaProperties.put("hibernate.format_sql", jpaShowSql);
        jpaProperties.put("hibernate.hbm2ddl.auto", jpaDdlAuto);
        jpaProperties.put("hibernate.dialect", jpaDialect);
        jpaProperties.put("org.hibernate.flushMode", FlushModeType.COMMIT.toString());
        jpaProperties.put("hibernate.current_session_context_class", sessionContext);
        jpaProperties.put("hibernate.id.new_generator_mappings", false); // true only when sequence is managed in
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.show_sql", jpaShowSql);
        jpaProperties.put("hibernate.format_sql", jpaShowSql);
        return jpaProperties;
    }

    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }
}
