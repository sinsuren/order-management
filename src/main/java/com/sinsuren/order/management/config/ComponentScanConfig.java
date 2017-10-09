package com.sinsuren.order.management.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by surender.s on 07/10/17.
 */
@Configuration
@ComponentScan(value = {"com.sinsuren.order.management"})
@EnableJpaRepositories(value = {"com.sinsuren.order.management"})
@EntityScan(value = {"com.sinsuren.order.management"})
public class ComponentScanConfig {
}
