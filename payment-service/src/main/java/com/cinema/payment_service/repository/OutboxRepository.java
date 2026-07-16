package com.cinema.payment_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.payment_service.entity.OutboxEvent;
import com.cinema.payment_service.enums.OutboxStatus;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus status);

}
