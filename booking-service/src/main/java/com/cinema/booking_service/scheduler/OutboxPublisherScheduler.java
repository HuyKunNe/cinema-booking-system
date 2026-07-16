package com.cinema.booking_service.scheduler;

import com.cinema.booking_service.entity.OutboxEvent;
import com.cinema.booking_service.enums.OutboxStatus;
import com.cinema.booking_service.outbox.OutboxDispatcher;
import com.cinema.booking_service.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisherScheduler {

    private final OutboxRepository repository;

    private final OutboxDispatcher dispatcher;

    @Scheduled(fixedDelayString = "${outbox.publisher.delay}")
    @Transactional
    public void publish() {

        List<OutboxEvent> events = repository.findTop100ByStatusOrderByCreatedAtAsc(
                OutboxStatus.NEW);

        for (OutboxEvent event : events) {

            try {

                dispatcher.dispatch(event);
                event.setStatus(OutboxStatus.SENT);
                event.setProcessedAt(LocalDateTime.now());

            } catch (Exception ex) {

                log.error("Cannot publish outbox event {}", event.getId(), ex);

            }

        }

    }

}