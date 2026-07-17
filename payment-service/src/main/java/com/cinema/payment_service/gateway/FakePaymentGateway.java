package com.cinema.payment_service.gateway;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.cinema.payment_service.entity.Payment;

@Component
public class FakePaymentGateway
        implements PaymentGateway {

    @Override
    public PaymentGatewayResult pay(Payment payment) {

        boolean success = ThreadLocalRandom.current()
                .nextInt(100) < 80;

        if (success) {

            return PaymentGatewayResult.builder()
                    .success(true)
                    .transactionId(
                            UUID.randomUUID().toString())
                    .message("SUCCESS")
                    .build();
        }

        return PaymentGatewayResult.builder()
                .success(false)
                .message("Payment rejected")
                .build();
    }

}