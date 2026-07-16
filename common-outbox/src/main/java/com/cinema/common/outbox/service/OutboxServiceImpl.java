package com.cinema.common.outbox.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.common.outbox.entity.OutboxEvent;
import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxEventType;
import com.cinema.common.outbox.enums.OutboxStatus;
import com.cinema.common.outbox.repository.OutboxRepository;
import com.cinema.common.outbox.serializer.EventSerializer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

        private final OutboxRepository outboxRepository;

        private final EventSerializer serializer;

        @Override
        @Transactional
        public void save(
                        AggregateType aggregateType,
                        Long aggregateId,
                        OutboxEventType eventType,
                        Object payload) {

                String jsonPayload = serializer.serialize(payload);

                OutboxEvent event = OutboxEvent.builder()
                                .aggregateType(aggregateType)
                                .aggregateId(aggregateId)
                                .eventType(eventType)
                                .payload(jsonPayload)
                                .status(OutboxStatus.NEW)
                                .build();

                outboxRepository.save(event);

        }

}