package com.cinema.event;

import java.time.LocalDateTime;

public record SeatReservedEvent(

        Long bookingId,

        Long userId,

        Long showtimeId,

        String seatNumber,

        LocalDateTime reservedAt

) {
}