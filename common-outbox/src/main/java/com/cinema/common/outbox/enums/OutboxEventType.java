package com.cinema.common.outbox.enums;

import com.cinema.event.PaymentSuccessEvent;
import com.cinema.event.SeatReservedEvent;

import lombok.Getter;

@Getter
public enum OutboxEventType {

  SEAT_RESERVED(
      "seat-reserved",
      SeatReservedEvent.class),

  PAYMENT_SUCCESS(
      "payment-success",
      PaymentSuccessEvent.class);

  // PAYMENT_FAILED(
  // "payment-failed",
  // pPaymentFailedEvent.class),

  // SEAT_RELEASED(
  // "seat-released",
  // SeatReleasedEvent.class);

  private final String topic;

  private final Class<?> eventClass;

  OutboxEventType(
      String topic,
      Class<?> eventClass) {

    this.topic = topic;
    this.eventClass = eventClass;

  }

}