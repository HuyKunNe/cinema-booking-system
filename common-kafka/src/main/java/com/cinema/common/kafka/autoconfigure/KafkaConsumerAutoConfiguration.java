package com.cinema.common.kafka.autoconfigure;

import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfiguration
public class KafkaConsumerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public ConsumerFactory<String, Object> consumerFactory(
      KafkaProperties properties,
      ObjectMapper kafkaObjectMapper) {

    Map<String, Object> config = properties.buildConsumerProperties(null);

    JsonDeserializer<Object> deserializer = new JsonDeserializer<>(
        kafkaObjectMapper);

    return new DefaultKafkaConsumerFactory<>(
        config,
        new StringDeserializer(),
        deserializer);
  }

  @Bean
  @ConditionalOnMissingBean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
      ConsumerFactory<String, Object> consumerFactory) {

    ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(
        consumerFactory);

    return factory;

  }

}