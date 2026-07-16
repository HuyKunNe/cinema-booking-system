package com.cinema.common.kafka.autoconfigure;

import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfiguration
public class KafkaProducerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public ProducerFactory<String, Object> producerFactory(
      KafkaProperties properties,
      ObjectMapper kafkaObjectMapper) {

    Map<String, Object> config = properties.buildProducerProperties(null);

    return new DefaultKafkaProducerFactory<>(
        config,
        new StringSerializer(),
        new JsonSerializer<>(kafkaObjectMapper));

  }

  @Bean
  @ConditionalOnMissingBean
  public KafkaTemplate<String, Object> kafkaTemplate(
      ProducerFactory<String, Object> factory) {

    return new KafkaTemplate<>(factory);

  }

}