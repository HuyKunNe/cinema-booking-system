package com.cinema.common.outbox.publisher;

import com.cinema.common.outbox.enums.AggregateType;

public interface EventPublisher {

    void publish(
            AggregateType aggregateType,
            Long aggregateId,
            Object event
    );

}