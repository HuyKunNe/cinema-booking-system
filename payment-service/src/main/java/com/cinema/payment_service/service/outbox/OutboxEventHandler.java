package com.cinema.payment_service.service.outbox;

import com.cinema.payment_service.entity.OutboxEvent;
import com.cinema.payment_service.enums.OutboxEventType;

public interface OutboxEventHandler {

    OutboxEventType getType();

    void handle(OutboxEvent event) throws Exception;

}