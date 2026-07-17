package com.cinema.payment_service.gateway;

import com.cinema.payment_service.entity.Payment;

public interface PaymentGateway {

    PaymentGatewayResult pay(Payment payment);

}