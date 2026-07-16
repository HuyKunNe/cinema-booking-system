package com.cinema.common.kafka.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
    JacksonAutoConfiguration.class,
    KafkaProducerAutoConfiguration.class,
    KafkaConsumerAutoConfiguration.class
})
public class CommonKafkaAutoConfiguration {

}