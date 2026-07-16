package com.cinema.common.outbox.publisher;

import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxEventType;

public interface EventPublisher {

    void publish(
            AggregateType booking,
            Long aggregateId,
            OutboxEventType eventType,
            Object event);

}