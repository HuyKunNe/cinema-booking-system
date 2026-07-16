package com.cinema.payment_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
    "com.cinema.payment_service.repository",
    "com.cinema.common.outbox.repository"
})
public class JpaRepositoryConfig {

}