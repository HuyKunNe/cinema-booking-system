package com.cinema.booking_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinema.booking_service.entity.ShowSeat;

import jakarta.persistence.LockModeType;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

        Optional<ShowSeat> findByShowtimeIdAndSeatNumber(
                        Long showtimeId,
                        String seatNumber);


        List<ShowSeat> findByShowtimeId(Long showtimeId);

        @Lock(LockModeType.PESSIMISTIC_WRITE)
        @Query("""
                            SELECT s
                            FROM ShowSeat s
                            WHERE s.showtimeId = :showtimeId
                            AND s.seatNumber IN :seatNumbers
                            ORDER BY s.seatNumber
                        """)
        List<ShowSeat> lockSeats(
                        @Param("showtimeId") Long showtimeId,

                        @Param("seatNumbers") List<String> seatNumbers);
}
