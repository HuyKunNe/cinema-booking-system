package com.cinema.booking_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cinema.booking_service.service.BookingService;
import com.cinema.common.kafka.constants.KafkaTopics;
import com.cinema.common.outbox.service.IdempotencyService;
import com.cinema.event.PaymentSuccessEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentSuccessConsumer {

    private static final String CONSUMER = "booking-payment-success";

    private final BookingService bookingService;

    private final IdempotencyService idempotencyService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_SUCCESS, groupId = "booking-group")
    public void consume(
            PaymentSuccessEvent event) {

        if (idempotencyService.alreadyProcessed(
                event.eventId(),
                "booking-payment-success")) {

            return;

        }

        bookingService.confirmBooking(event);

        idempotencyService.markProcessed(
                event.eventId(),
                CONSUMER);

    }

}
