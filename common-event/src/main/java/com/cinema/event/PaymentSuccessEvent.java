package com.cinema.event;

import java.time.LocalDateTime;

public record PaymentSuccessEvent(

        Long paymentId,

        Long bookingId,

        Long userId,
        LocalDateTime paidAt) {
}