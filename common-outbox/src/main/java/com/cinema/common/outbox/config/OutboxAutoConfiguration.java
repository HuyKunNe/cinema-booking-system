package com.cinema.common.outbox.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@EntityScan(basePackages = "com.cinema.common.outbox.entity")
@ComponentScan(basePackages = "com.cinema.common.outbox")
public class OutboxAutoConfiguration {

}