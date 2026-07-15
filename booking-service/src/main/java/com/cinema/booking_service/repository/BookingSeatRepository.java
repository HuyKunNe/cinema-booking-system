package com.cinema.booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.booking_service.entity.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

}
