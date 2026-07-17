package com.cinema.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PaymentSuccessEvent(

        UUID eventId,

        LocalDateTime occurredAt,

        Long paymentId,

        Long bookingId,

        Long userId,

        String transactionId,

        LocalDateTime paidAt) {
}