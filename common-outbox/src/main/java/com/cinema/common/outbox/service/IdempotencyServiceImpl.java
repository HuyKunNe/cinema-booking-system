package com.cinema.common.outbox.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cinema.common.outbox.entity.ProcessedEvent;
import com.cinema.common.outbox.repository.ProcessedEventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IdempotencyServiceImpl
        implements IdempotencyService {

    private final ProcessedEventRepository repository;

    @Override
    public boolean alreadyProcessed(
            UUID eventId,
            String consumerName) {

        return repository.existsByEventIdAndConsumerName(
                eventId,
                consumerName);

    }

    @Override
    public void markProcessed(
            UUID eventId,
            String consumerName) {

        repository.save(

                ProcessedEvent.builder()
                        .eventId(eventId)
                        .consumerName(consumerName)
                        .processedAt(LocalDateTime.now())
                        .build());

    }

}