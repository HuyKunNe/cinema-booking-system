package com.cinema.booking_service.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;
import com.cinema.booking_service.entity.Booking;
import com.cinema.booking_service.entity.BookingSeat;
import com.cinema.booking_service.entity.ShowSeat;
import com.cinema.booking_service.enums.AggregateType;
import com.cinema.booking_service.enums.BookingStatus;
import com.cinema.booking_service.enums.OutboxEventType;
import com.cinema.booking_service.enums.SeatStatus;
import com.cinema.booking_service.exception.SeatAlreadyBookedException;
import com.cinema.booking_service.exception.SeatNotFoundException;
import com.cinema.booking_service.repository.BookingRepository;
import com.cinema.booking_service.repository.BookingSeatRepository;
import com.cinema.booking_service.repository.ShowSeatRepository;
import com.cinema.booking_service.service.BookingTransactionService;
import com.cinema.booking_service.service.outbox.OutboxService;
import com.cinema.event.SeatReservedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingTransactionServiceImpl implements BookingTransactionService {

        private final ShowSeatRepository showSeatRepository;

        private final BookingRepository bookingRepository;

        private final BookingSeatRepository bookingSeatRepository;
        private final ObjectMapper objectMapper;
        private final OutboxService outboxService;

        @Value("${booking.hold-duration-minutes}")
        private long holdDurationMinutes;

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
                LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(holdDurationMinutes);
                Booking booking = Booking.builder()
                                .userId(request.getUserId())
                                .showtimeId(request.getShowtimeId())
                                .expiredAt(expiredAt)
                                .status(BookingStatus.RESERVED)
                                .build();

                bookingRepository.save(booking);

                List<BookingSeat> bookingSeats = seats.stream()
                                .map(seat -> {
                                        seat.setStatus(SeatStatus.RESERVED);
                                        seat.setReservedBy(request.getUserId());
                                        seat.setReservedUntil(expiredAt);
                                        return BookingSeat.builder()
                                                        .bookingId(booking.getId())
                                                        .showSeatId(seat.getId())
                                                        .build();
                                }).toList();
                showSeatRepository.saveAll(seats);
                bookingSeatRepository.saveAll(bookingSeats);

                SeatReservedEvent event = SeatReservedEvent.builder()
                                .bookingId(booking.getId())
                                .userId(request.getUserId())
                                .showtimeId(request.getShowtimeId())
                                .seatNumbers(request.getSeatNumbers())
                                .createdAt(LocalDateTime.now())
                                .build();

                outboxService.save(
                                AggregateType.BOOKING,
                                booking.getId(),
                                OutboxEventType.SEAT_RESERVED,
                                event);

                return ReserveSeatResponse.builder()
                                .bookingId(booking.getId())
                                .status(booking.getStatus())
                                .message("Reserved")
                                .build();

        }

        public String toJson(Object object) {
                try {
                        return objectMapper.writeValueAsString(object);
                } catch (JsonProcessingException e) {
                        throw new IllegalStateException("Cannot convert object to JSON", e);
                }
        }

}
