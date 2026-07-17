package com.cinema.booking_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cinema.booking_service.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
        @Query("""
                        SELECT b
                        FROM Booking b
                        WHERE b.status='RESERVED'
                        AND b.expiredAt <= :now
                        """)
        List<Booking> findExpiredBookings(
                        LocalDateTime now);
}
