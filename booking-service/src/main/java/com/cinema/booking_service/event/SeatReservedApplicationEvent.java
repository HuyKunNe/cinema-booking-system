package com.cinema.booking_service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class SeatReservedApplicationEvent
        extends ApplicationEvent {

    private final Long bookingId;

    private final Long userId;

    private final Long showtimeId;

    private final List<String> seatNumbers;

    public SeatReservedApplicationEvent(
            Object source,
            Long bookingId,
            Long userId,
            Long showtimeId,
            List<String> seatNumbers) {

        super(source);

        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumbers = seatNumbers;
    }
}