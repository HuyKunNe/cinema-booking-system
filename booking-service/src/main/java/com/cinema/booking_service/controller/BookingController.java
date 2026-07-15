package com.cinema.booking_service.controller;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.dto.response.ReserveSeatResponse;
import com.cinema.booking_service.service.BookingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/reserve")
    public ResponseEntity<ReserveSeatResponse> reserve(
            @Valid @RequestBody ReserveSeatRequest request) {

        return ResponseEntity.ok(
                bookingService.reserveSeat(request));

    }

}