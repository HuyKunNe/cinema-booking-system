package com.cinema.booking_service.service;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;

public interface BookingService {

    ReserveSeatResponse reserveSeat(ReserveSeatRequest request);

    void cancelBooking(Long bookingId);

    void confirmBooking(Long bookingId);

}