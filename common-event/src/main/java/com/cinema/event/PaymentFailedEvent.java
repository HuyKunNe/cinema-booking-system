package com.cinema.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PaymentFailedEvent(

    UUID eventId,

    LocalDateTime occurredAt,

    Long paymentId,

    Long bookingId,

    Long userId,

    String reason,

    LocalDateTime failedAt

) {
}