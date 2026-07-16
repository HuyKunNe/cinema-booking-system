package com.cinema.booking_service.service.outbox;

import com.cinema.booking_service.enums.AggregateType;
import com.cinema.booking_service.enums.OutboxEventType;

public interface OutboxService {

    void save(
            AggregateType aggregateType,
            Long aggregateId,
            OutboxEventType eventType,
            Object payload
    );

}