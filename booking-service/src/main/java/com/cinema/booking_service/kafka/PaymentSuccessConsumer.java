package com.cinema.booking_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cinema.booking_service.service.BookingService;
import com.cinema.common.kafka.constants.KafkaTopics;
import com.cinema.event.PaymentSuccessEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessConsumer {

    private final BookingService bookingService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_SUCCESS, groupId = "booking-group")
    public void consume(PaymentSuccessEvent event) {

        log.info("========== PAYMENT SUCCESS CONSUMER ==========");
        log.info("Receive PaymentSuccessEvent: {}", event);

        try {

            bookingService.confirmBooking(event);

            log.info("Booking confirmed. bookingId={}", event.bookingId());

        } catch (Exception ex) {

            log.error("Failed to confirm booking. bookingId={}",
                    event.bookingId(),
                    ex);

            // throw để Kafka retry
            throw ex;
        }

        log.info("==============================================");
    }

}