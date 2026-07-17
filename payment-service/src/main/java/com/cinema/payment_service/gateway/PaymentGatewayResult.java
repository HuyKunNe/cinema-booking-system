package com.cinema.payment_service.gateway;

import lombok.Builder;

@Builder
public record PaymentGatewayResult(

        boolean success,

        String transactionId,

        String message

) {
}