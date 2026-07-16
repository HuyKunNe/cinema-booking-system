package com.cinema.payment_service.service.outbox;

import com.cinema.payment_service.enums.AggregateType;
import com.cinema.payment_service.enums.OutboxEventType;

public interface OutboxService {

    void save(
            AggregateType aggregateType,
            Long aggregateId,
            OutboxEventType eventType,
            Object payload
    );

}