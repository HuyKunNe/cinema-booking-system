package com.cinema.payment_service.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cinema.payment_service.service.outbox.OutboxPublishService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxPublisherScheduler {

    private final OutboxPublishService service;

    @Scheduled(fixedDelayString = "${outbox.publisher.delay}")
    public void publish() {

        service.publish();

    }

}