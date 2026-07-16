package com.cinema.payment_service.enums;

public enum OutboxEventType {
    SEAT_RESERVED,

    PAYMENT_SUCCESS,

    PAYMENT_FAILED,

    BOOKING_CANCELLED
}
