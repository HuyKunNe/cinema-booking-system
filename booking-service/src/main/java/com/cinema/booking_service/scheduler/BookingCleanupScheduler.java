package com.cinema.booking_service.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cinema.booking_service.service.BookingCleanupService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingCleanupScheduler {

    private final BookingCleanupService cleanupService;

    @Scheduled(fixedDelayString = "${booking.cleanup-delay}")
    public void cleanup() {

        cleanupService.releaseExpiredBookings();

    }

}