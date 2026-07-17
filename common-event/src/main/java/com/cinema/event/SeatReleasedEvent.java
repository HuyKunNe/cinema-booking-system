package com.cinema.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record SeatReleasedEvent(

        UUID eventId,

        LocalDateTime occurredAt,

        Long bookingId,

        Long showtimeId,

        List<String> seatNumbers

) {
}