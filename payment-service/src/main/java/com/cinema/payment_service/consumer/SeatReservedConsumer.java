package com.cinema.payment_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cinema.common.kafka.constants.KafkaTopics;
import com.cinema.event.SeatReservedEvent;
import com.cinema.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatReservedConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = KafkaTopics.SEAT_RESERVED, groupId = "payment-group")
    public void consume(
            SeatReservedEvent event) {

        paymentService.process(event);

    }

}