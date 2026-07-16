package com.cinema.common.outbox.service;

import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxEventType;

public interface OutboxService {

  void save(
      AggregateType aggregateType,
      Long aggregateId,
      OutboxEventType eventType,
      Object payload);

}