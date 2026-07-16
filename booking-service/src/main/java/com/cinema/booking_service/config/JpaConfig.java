package com.cinema.booking_service.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {
    "com.cinema.booking_service.entity",
    "com.cinema.common.outbox.entity"
})
public class JpaConfig {

}