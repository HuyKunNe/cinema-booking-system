package com.cinema.common.outbox.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.common.outbox.entity.ProcessedEvent;

public interface ProcessedEventRepository
        extends JpaRepository<ProcessedEvent, Long> {

    boolean existsByEventIdAndConsumerName(
            UUID eventId,
            String consumerName);

}