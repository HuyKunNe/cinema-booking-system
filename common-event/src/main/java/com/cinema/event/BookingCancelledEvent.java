package com.cinema.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record BookingCancelledEvent(

        UUID eventId,

        LocalDateTime occurredAt,

        Long bookingId,

        Long userId,

        Long showtimeId,

        String reason

) {
}