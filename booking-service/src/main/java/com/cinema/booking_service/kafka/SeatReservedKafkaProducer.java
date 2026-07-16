package com.cinema.booking_service.kafka;

import com.cinema.event.SeatReservedEvent;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatReservedKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "seat-reserved";

    public void send(SeatReservedEvent event) {

        kafkaTemplate.send(TOPIC, event.bookingId().toString(), event);

    }

}