package com.cinema.booking_service.service.imp;

import org.springframework.stereotype.Service;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;
import com.cinema.booking_service.service.BookingService;
import com.cinema.booking_service.service.BookingTransactionService;
import com.cinema.booking_service.service.lock.DistributedLockService;

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
