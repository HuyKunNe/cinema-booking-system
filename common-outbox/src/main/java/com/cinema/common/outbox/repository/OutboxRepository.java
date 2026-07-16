package com.cinema.common.outbox.repository;


import com.cinema.common.outbox.entity.OutboxEvent;
import com.cinema.common.outbox.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OutboxRepository
        extends JpaRepository<OutboxEvent,Long> {



    List<OutboxEvent> findTop100ByStatusOrderByCreatedAt(
            OutboxStatus status
    );

}