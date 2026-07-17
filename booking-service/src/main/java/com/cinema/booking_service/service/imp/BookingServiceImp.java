package com.cinema.booking_service.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;
import com.cinema.booking_service.entity.Booking;
import com.cinema.booking_service.entity.BookingSeat;
import com.cinema.booking_service.entity.ShowSeat;
import com.cinema.booking_service.enums.BookingStatus;
import com.cinema.booking_service.enums.SeatStatus;
import com.cinema.booking_service.repository.BookingRepository;
import com.cinema.booking_service.repository.BookingSeatRepository;
import com.cinema.booking_service.repository.ShowSeatRepository;
import com.cinema.booking_service.service.BookingService;
import com.cinema.booking_service.service.BookingTransactionService;
import com.cinema.booking_service.service.lock.DistributedLockService;
import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxEventType;
import com.cinema.common.outbox.publisher.EventPublisher;
import com.cinema.event.PaymentFailedEvent;
import com.cinema.event.PaymentSuccessEvent;
import com.cinema.event.SeatReleasedEvent;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImp implements BookingService {

        private final DistributedLockService lockService;

        private final BookingTransactionService transactionService;

        private final BookingRepository bookingRepository;

        private final ShowSeatRepository showSeatRepository;

        private final BookingSeatRepository bookingSeatRepository;

        private final EventPublisher eventPublisher;

        @Override
        public ReserveSeatResponse reserveSeat(ReserveSeatRequest request) {

                String key = "seat-lock:" + request.getShowtimeId() + ":" + String.join(",", request.getSeatNumbers());

                // Redis distributed lock to prevent concurrent booking for the same seats
                return lockService.executeWithLock(key, () -> transactionService.reserve(request));

        }

        @Override
        @Transactional
        public void cancelBooking(PaymentFailedEvent event) {

                Booking booking = bookingRepository.findById(event.bookingId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Booking not found: " + event.bookingId()));

                if (booking.getStatus() == BookingStatus.CANCELLED) {

                        log.info("Booking {} already cancelled", booking.getId());
                        return;
                }

                booking.setStatus(BookingStatus.CANCELLED);

                SeatReleasedEvent seatReleasedEvent = SeatReleasedEvent.builder()
                                .bookingId(booking.getId())
                                .showtimeId(booking.getShowtimeId())
                                .build();

                eventPublisher.publish(
                                AggregateType.BOOKING,
                                booking.getId(),
                                OutboxEventType.SEAT_RELEASED,
                                seatReleasedEvent);

                log.info("Booking {} cancelled", booking.getId());
        }

        @Override
        @Transactional
        public void confirmBooking(PaymentSuccessEvent event) {

                Booking booking = bookingRepository.findById(event.bookingId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Booking not found: " + event.bookingId()));

                if (booking.getStatus() == BookingStatus.CONFIRMED) {

                        log.info("Booking {} already confirmed", booking.getId());
                        return;
                }

                booking.setStatus(BookingStatus.CONFIRMED);

                List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(
                                booking.getId());

                for (BookingSeat bookingSeat : bookingSeats) {

                        ShowSeat showSeat = showSeatRepository.findById(
                                        bookingSeat.getShowSeatId())
                                        .orElseThrow();

                        showSeat.setStatus(SeatStatus.BOOKED);
                }

                log.info(
                                "Booking {} confirmed successfully",
                                booking.getId());
        }

}
