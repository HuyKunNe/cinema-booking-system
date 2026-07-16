package com.cinema.common.outbox.topic;

import org.springframework.stereotype.Component;

import com.cinema.common.outbox.enums.OutboxEventType;

@Component
public class DefaultKafkaTopicResolver
    implements KafkaTopicResolver {

  @Override
  public String resolve(OutboxEventType eventType) {

    return eventType.getTopic();

  }

}