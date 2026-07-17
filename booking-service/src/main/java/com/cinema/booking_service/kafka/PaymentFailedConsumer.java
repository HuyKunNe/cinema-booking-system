package com.cinema.booking_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cinema.booking_service.service.BookingService;
import com.cinema.common.kafka.constants.KafkaTopics;
import com.cinema.event.PaymentFailedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentFailedConsumer {

        private final BookingService bookingService;

        @KafkaListener(topics = KafkaTopics.PAYMENT_FAILED, groupId = "booking-group")
        public void consume(PaymentFailedEvent event) {

                bookingService.cancelBooking(event);

        }

}
