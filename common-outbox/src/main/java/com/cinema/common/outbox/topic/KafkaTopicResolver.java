package com.cinema.common.outbox.topic;

import com.cinema.common.outbox.enums.OutboxEventType;

public interface KafkaTopicResolver {

    String resolve(OutboxEventType eventType);

}