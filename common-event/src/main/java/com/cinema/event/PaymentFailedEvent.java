package com.cinema.event;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record PaymentFailedEvent(

        Long paymentId,

        Long bookingId,

        Long userId,

        String reason,

        LocalDateTime failedAt) {
}