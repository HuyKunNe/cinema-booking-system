package com.cinema.booking_service.service.imp;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;
import com.cinema.booking_service.entity.Booking;
import com.cinema.booking_service.entity.BookingSeat;
import com.cinema.booking_service.entity.ShowSeat;
import com.cinema.booking_service.enums.BookingStatus;
import com.cinema.booking_service.enums.SeatStatus;
import com.cinema.booking_service.exception.SeatAlreadyBookedException;
import com.cinema.booking_service.exception.SeatNotFoundException;
import com.cinema.booking_service.repository.BookingRepository;
import com.cinema.booking_service.repository.BookingSeatRepository;
import com.cinema.booking_service.repository.ShowSeatRepository;
import com.cinema.booking_service.service.BookingService;
import com.cinema.booking_service.service.BookingTransactionService;
import com.cinema.booking_service.service.lock.DistributedLockService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {

        private final DistributedLockService lockService;

        private final BookingTransactionService transactionService;

        @Override
        public ReserveSeatResponse reserveSeat(
                        ReserveSeatRequest request) {

                String key = "seat-lock:"
                                + request.getShowtimeId()
                                + ":"
                                + String.join(
                                                ",",
                                                request.getSeatNumbers());

                return lockService.executeWithLock(
                                key,
                                () -> transactionService.reserve(request));

        }

        @Override
        public void cancelBooking(Long bookingId) {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void confirmBooking(Long bookingId) {
                throw new UnsupportedOperationException("Not supported yet.");
        }

}
