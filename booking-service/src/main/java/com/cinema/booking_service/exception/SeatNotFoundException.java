package com.cinema.booking_service.exception;

public class SeatNotFoundException extends RuntimeException {

    public SeatNotFoundException(String message) {
        super(message);
    }

}