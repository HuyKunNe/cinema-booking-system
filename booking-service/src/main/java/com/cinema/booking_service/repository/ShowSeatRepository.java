package com.cinema.booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.cinema.booking_service.entity.ShowSeat;

import jakarta.persistence.LockModeType;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select s
            from ShowSeat s
            where s.showtimeId=:showtimeId
            and s.seatNumber=:seat
            """)
    ShowSeat lockSeat(
            Long showtimeId,
            String seat);
}
