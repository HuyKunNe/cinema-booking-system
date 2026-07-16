package com.cinema.booking_service.service.imp;

import org.springframework.stereotype.Service;

import com.cinema.booking_service.service.BookingCleanupService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingCleanupServiceImp implements BookingCleanupService {
    @Override
    public void releaseExpiredBookings() {
        // Implementation for releasing expired bookings
    }

}
