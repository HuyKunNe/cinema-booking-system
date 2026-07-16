package com.cinema.booking_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
                "com.cinema.booking_service.repository",
                "com.cinema.common.outbox.repository"
})
public class JpaRepositoryConfig {

}