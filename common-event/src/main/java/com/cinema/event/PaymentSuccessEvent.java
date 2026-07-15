package com.cinema.event;

import java.time.LocalDateTime;

public record PaymentSuccessEvent(

    Long bookingId,

    Long userId,

    Double amount,

    LocalDateTime paidAt

) {
}