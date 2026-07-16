package com.cinema.booking_service.service.imp;

import org.springframework.stereotype.Service;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;
import com.cinema.booking_service.entity.Booking;
import com.cinema.booking_service.enums.BookingStatus;
import com.cinema.booking_service.repository.BookingRepository;
import com.cinema.booking_service.service.BookingService;
import com.cinema.booking_service.service.BookingTransactionService;
import com.cinema.booking_service.service.lock.DistributedLockService;
import com.cinema.event.PaymentSuccessEvent;

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

        @Override
        public ReserveSeatResponse reserveSeat(ReserveSeatRequest request) {

                String key = "seat-lock:" + request.getShowtimeId() + ":" + String.join(",", request.getSeatNumbers());

                // Redis distributed lock to prevent concurrent booking for the same seats
                return lockService.executeWithLock(key, () -> transactionService.reserve(request));

        }

        @Override
        public void cancelBooking(Long bookingId) {
                throw new UnsupportedOperationException("Not supported yet.");
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

                bookingRepository.save(booking);

                log.info("Booking {} confirmed successfully", booking.getId());
        }

}
