package com.cinema.payment_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cinema.common.kafka.constants.KafkaTopics;
import com.cinema.event.SeatReservedEvent;
import com.cinema.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeatReservedConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = KafkaTopics.SEAT_RESERVED, groupId = "payment-group")
    public void consume(SeatReservedEvent event) {

        System.out.println("Payment received: " + event);

        paymentService.process(event);

    }

}