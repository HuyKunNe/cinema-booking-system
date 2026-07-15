package com.cinema.booking_service.event;

import com.cinema.booking_service.kafka.SeatReservedKafkaProducer;
import com.cinema.event.SeatReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SeatReservedEventListener {

    private final SeatReservedKafkaProducer producer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(
            SeatReservedApplicationEvent event) {

        SeatReservedEvent kafkaEvent = SeatReservedEvent.builder()

                .bookingId(
                        event.getBookingId())

                .userId(
                        event.getUserId())

                .showtimeId(
                        event.getShowtimeId())

                .seatNumbers(
                        event.getSeatNumbers())

                .createdAt(
                        LocalDateTime.now())

                .build();

        producer.send(kafkaEvent);

    }

}