package com.cinema.booking_service.service.outbox;

import com.cinema.booking_service.entity.OutboxEvent;
import com.cinema.booking_service.enums.AggregateType;
import com.cinema.booking_service.enums.OutboxEventType;
import com.cinema.booking_service.enums.OutboxStatus;
import com.cinema.booking_service.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxServiceImpl implements OutboxService {

    private final OutboxRepository outboxRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void save(
            AggregateType aggregateType,
            Long aggregateId,
            OutboxEventType eventType,
            Object payload) {

        try {

            String jsonPayload = objectMapper.writeValueAsString(payload);

            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .aggregateType(aggregateType)
                    .aggregateId(aggregateId)
                    .eventType(eventType)
                    .payload(jsonPayload)
                    .status(OutboxStatus.NEW)
                    .build();

            outboxRepository.save(outboxEvent);

        } catch (JsonProcessingException e) {

            log.error("Cannot serialize outbox payload", e);

            throw new RuntimeException(
                    "Cannot serialize outbox payload", e);

        }

    }

}