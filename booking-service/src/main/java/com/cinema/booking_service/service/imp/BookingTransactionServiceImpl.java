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
import com.cinema.booking_service.exception.SeatAlreadyBookedException;
import com.cinema.booking_service.exception.SeatNotFoundException;
import com.cinema.booking_service.repository.BookingRepository;
import com.cinema.booking_service.repository.BookingSeatRepository;
import com.cinema.booking_service.repository.ShowSeatRepository;
import com.cinema.booking_service.service.BookingTransactionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingTransactionServiceImpl
                implements BookingTransactionService {

        private final ShowSeatRepository showSeatRepository;

        private final BookingRepository bookingRepository;

        private final BookingSeatRepository bookingSeatRepository;

        @Override
        @Transactional
        public ReserveSeatResponse reserve(
                        ReserveSeatRequest request) {

                List<ShowSeat> seats = showSeatRepository.lockSeats(
                                request.getShowtimeId(),
                                request.getSeatNumbers());

                if (seats.size() != request.getSeatNumbers().size()) {

                        throw new SeatNotFoundException(
                                        "Seat not found");
                }

                for (ShowSeat seat : seats) {

                        if (seat.getStatus() != SeatStatus.AVAILABLE) {

                                throw new SeatAlreadyBookedException(
                                                "Seat "
                                                                + seat.getSeatNumber()
                                                                + " already booked");
                        }

                }

                Booking booking = Booking.builder()
                                .userId(request.getUserId())
                                .showtimeId(request.getShowtimeId())
                                .status(BookingStatus.RESERVED)
                                .build();

                bookingRepository.save(booking);

                List<BookingSeat> bookingSeats = seats.stream()
                                .map(seat -> {

                                        seat.setStatus(
                                                        SeatStatus.RESERVED);

                                        seat.setReservedBy(
                                                        request.getUserId());

                                        return BookingSeat.builder()
                                                        .bookingId(
                                                                        booking.getId())
                                                        .showSeatId(
                                                                        seat.getId())
                                                        .build();

                                })
                                .toList();

                bookingSeatRepository.saveAll(
                                bookingSeats);

                return ReserveSeatResponse.builder()
                                .bookingId(booking.getId())
                                .status(booking.getStatus())
                                .message("Reserved")
                                .build();

        }

}
