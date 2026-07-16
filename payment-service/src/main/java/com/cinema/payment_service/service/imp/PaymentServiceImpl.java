package com.cinema.payment_service.service.imp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxEventType;
import com.cinema.common.outbox.service.OutboxService;
import com.cinema.event.PaymentSuccessEvent;
import com.cinema.event.SeatReservedEvent;
import com.cinema.payment_service.entity.Payment;
import com.cinema.payment_service.enums.PaymentStatus;
import com.cinema.payment_service.repository.PaymentRepository;
import com.cinema.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl
                implements PaymentService {

        private final PaymentRepository paymentRepository;

        private final OutboxService outboxService;

        @Override
        @Transactional
        public void process(
                        SeatReservedEvent event) {

                Payment payment = Payment.builder()
                                .bookingId(event.bookingId())
                                .userId(event.userId())
                                .status(PaymentStatus.SUCCESS)
                                .build();

                paymentRepository.save(payment);

                PaymentSuccessEvent paymentEvent = new PaymentSuccessEvent(
                                payment.getId(),
                                payment.getBookingId(),
                                payment.getUserId(),
                                LocalDateTime.now());

                outboxService.save(
                                AggregateType.PAYMENT,
                                payment.getId(),
                                OutboxEventType.PAYMENT_SUCCESS,
                                paymentEvent);

        }

}