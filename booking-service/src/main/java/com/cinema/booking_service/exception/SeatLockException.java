package com.cinema.booking_service.exception;

public class SeatLockException extends RuntimeException {

    public SeatLockException() {
        super("Unable to acquire seat lock.");
    }

    public SeatLockException(String message) {
        super(message);
    }
}