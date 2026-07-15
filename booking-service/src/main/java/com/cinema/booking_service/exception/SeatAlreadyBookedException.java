package com.cinema.booking_service.exception;

public class SeatAlreadyBookedException extends RuntimeException {

    public SeatAlreadyBookedException() {
        super("Seat is already booked.");
    }

    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}