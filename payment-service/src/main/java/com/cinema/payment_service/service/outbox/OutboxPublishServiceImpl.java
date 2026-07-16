package com.cinema.payment_service.service.outbox;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.payment_service.entity.OutboxEvent;
import com.cinema.payment_service.enums.OutboxStatus;
import com.cinema.payment_service.repository.OutboxRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxPublishServiceImpl implements OutboxPublishService {

    private final OutboxRepository outboxRepository;

    private final OutboxDispatcher dispatcher;

    @Override
    @Transactional
    public void publish() {

        List<OutboxEvent> events = outboxRepository.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.NEW);
        for (OutboxEvent event : events) {
            try {
                dispatcher.dispatch(event);
                event.setStatus(OutboxStatus.SENT);
                event.setProcessedAt(LocalDateTime.now());

            } catch (Exception e) {
                log.error("Publish outbox failed id={}", event.getId(), e);

            }

        }

    }

}