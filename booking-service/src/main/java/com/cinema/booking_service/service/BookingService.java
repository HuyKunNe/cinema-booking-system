package com.cinema.booking_service.service;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;
import com.cinema.event.PaymentFailedEvent;
import com.cinema.event.PaymentSuccessEvent;

public interface BookingService {

    ReserveSeatResponse reserveSeat(ReserveSeatRequest request);

    void cancelBooking(PaymentFailedEvent event);

    void confirmBooking(PaymentSuccessEvent event);

}