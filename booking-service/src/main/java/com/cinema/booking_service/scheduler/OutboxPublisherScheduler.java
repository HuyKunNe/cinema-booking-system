package com.cinema.booking_service.scheduler;

import com.cinema.booking_service.service.outbox.OutboxPublishService;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxPublisherScheduler {

    private final OutboxPublishService service;

    @Scheduled(fixedDelayString = "${outbox.publisher.delay}")
    public void publish() {

        service.publish();

    }

}