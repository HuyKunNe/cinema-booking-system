package com.cinema.common.kafka.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@AutoConfiguration
public class JacksonAutoConfiguration {

    @Bean
    public ObjectMapper kafkaObjectMapper() {

        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(
                new JavaTimeModule());

        mapper.disable(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

}