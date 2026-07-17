package com.cinema.common.outbox.service;

import java.util.UUID;

public interface IdempotencyService {

    boolean alreadyProcessed(UUID eventId,
            String consumerName);

    void markProcessed(UUID eventId,
            String consumerName);

}