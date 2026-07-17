package com.cinema.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record BookingExpiredEvent(

        UUID eventId,

        LocalDateTime occurredAt,

        Long bookingId

) {
}
