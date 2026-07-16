package com.cinema.booking_service.kafka;

import com.cinema.booking_service.service.BookingService;
import com.cinema.event.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final BookingService bookingService;

    @KafkaListener(topics = "payment-success", groupId = "booking-group")
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