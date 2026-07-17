package com.cinema.payment_service.service;

import com.cinema.event.SeatReservedEvent;

public interface PaymentService {

    void process(SeatReservedEvent event);

}