package com.cinema.booking_service.kafka;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cinema.booking_service.entity.BookingSeat;
import com.cinema.booking_service.entity.ShowSeat;
import com.cinema.booking_service.enums.SeatStatus;
import com.cinema.booking_service.repository.BookingSeatRepository;
import com.cinema.booking_service.repository.ShowSeatRepository;
import com.cinema.common.kafka.constants.KafkaTopics;
import com.cinema.event.SeatReleasedEvent;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeatReleasedConsumer {

        private final ShowSeatRepository showSeatRepository;
        private final BookingSeatRepository bookingSeatRepository;

        @KafkaListener(topics = KafkaTopics.SEAT_RELEASED, groupId = "booking-group")
        @Transactional
        public void consume(SeatReleasedEvent event) {

                List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(
                                event.bookingId());

                for (BookingSeat bookingSeat : bookingSeats) {

                        ShowSeat showSeat = showSeatRepository.findById(bookingSeat.getShowSeatId()).orElseThrow();

                        showSeat.setStatus(SeatStatus.AVAILABLE);
                }
        }
}