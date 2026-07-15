package com.cinema.event;

import java.time.LocalDateTime;

public record BookingCancelledEvent(

    Long bookingId,

    String reason,

    LocalDateTime cancelledAt

) {
}