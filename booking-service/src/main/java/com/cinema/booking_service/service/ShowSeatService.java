package com.cinema.booking_service.service;

import java.util.List;

import com.cinema.booking_service.entity.ShowSeat;

public interface ShowSeatService {

    List<ShowSeat> lockSeats(
            Long showtimeId,
            List<String> seatNumbers);

    void releaseSeats(Long bookingId);

    void markBooked(Long bookingId);

}