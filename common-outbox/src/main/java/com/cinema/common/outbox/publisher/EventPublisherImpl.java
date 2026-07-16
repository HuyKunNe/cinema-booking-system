package com.cinema.common.outbox.publisher;

import org.springframework.stereotype.Component;

import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxEventType;
import com.cinema.common.outbox.service.OutboxService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventPublisherImpl implements EventPublisher {

  private final OutboxService outboxService;

  @Override
  public void publish(
      AggregateType aggregateType,
      Long aggregateId,
      OutboxEventType eventType,
      Object event) {

    outboxService.save(
        aggregateType,
        aggregateId,
        eventType,
        event);

  }

}