package com.cinema.payment_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cinema.event.SeatReservedEvent;
import com.cinema.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "seat-reserved",
            groupId = "payment-group"
    )
    public void consume(SeatReservedEvent event) {

        log.info("Receive event {}", event);

        paymentService.processPayment(event);
    }
}