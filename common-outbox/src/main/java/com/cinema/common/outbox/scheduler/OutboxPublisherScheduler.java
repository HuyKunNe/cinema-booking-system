package com.cinema.common.outbox.scheduler;

import com.cinema.common.outbox.entity.OutboxEvent;
import com.cinema.common.outbox.enums.OutboxStatus;
import com.cinema.common.outbox.publisher.KafkaEventPublisher;
import com.cinema.common.outbox.repository.OutboxRepository;
import com.cinema.common.outbox.topic.KafkaTopicResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisherScheduler {

        private final OutboxRepository repository;

        private final KafkaEventPublisher kafkaPublisher;

        private final KafkaTopicResolver topicResolver;

        private final ObjectMapper objectMapper;

        @Scheduled(fixedDelay = 5000)
        public void publishEvents() {

                List<OutboxEvent> events = repository
                                .findTop100ByStatusOrderByCreatedAt(
                                                OutboxStatus.NEW);

                for (OutboxEvent event : events) {

                        try {

                                Class<?> clazz = event.getEventType()
                                                .getEventClass();

                                String topic = topicResolver.resolve(
                                                event.getEventType());

                                Object payload = objectMapper.readValue(
                                                event.getPayload(),
                                                event.getEventType()
                                                                .getEventClass());

                                kafkaPublisher.publish(
                                                topic,
                                                event.getAggregateId(),
                                                payload);

                                event.setStatus(
                                                OutboxStatus.SENT);

                                event.setProcessedAt(
                                                LocalDateTime.now());

                                repository.save(event);

                                log.info(
                                                "Outbox published id={} topic={}",
                                                event.getId(),
                                                topic);

                        } catch (Exception e) {

                                log.error(
                                                "Publish outbox failed id={}",
                                                event.getId(),
                                                e);

                        }

                }

        }

}