package com.cinema.booking_service.outbox;

import com.cinema.booking_service.entity.OutboxEvent;
import com.cinema.booking_service.enums.OutboxEventType;
import com.cinema.booking_service.kafka.SeatReservedKafkaProducer;
import com.cinema.event.SeatReservedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatReservedEventHandler
        implements OutboxEventHandler {

    private final ObjectMapper objectMapper;

    private final SeatReservedKafkaProducer producer;

    @Override
    public OutboxEventType getType() {
        return OutboxEventType.SEAT_RESERVED;
    }

    @Override
    public void handle(OutboxEvent event) throws Exception {

        SeatReservedEvent seatReservedEvent =
                objectMapper.readValue(
                        event.getPayload(),
                        SeatReservedEvent.class
                );

        producer.send(seatReservedEvent);

    }

}