package com.cinema.booking_service.outbox;

import com.cinema.booking_service.entity.OutboxEvent;
import com.cinema.booking_service.enums.OutboxEventType;

public interface OutboxEventHandler {

    OutboxEventType getType();

    void handle(OutboxEvent event) throws Exception;

}