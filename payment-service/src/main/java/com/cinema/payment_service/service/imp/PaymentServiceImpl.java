package com.cinema.payment_service.service.imp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.cinema.event.PaymentSuccessEvent;
import com.cinema.event.SeatReservedEvent;
import com.cinema.payment_service.entity.Payment;
import com.cinema.payment_service.enums.AggregateType;
import com.cinema.payment_service.enums.OutboxEventType;
import com.cinema.payment_service.enums.PaymentStatus;
import com.cinema.payment_service.repository.PaymentRepository;
import com.cinema.payment_service.service.PaymentService;
import com.cinema.payment_service.service.outbox.OutboxService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OutboxService outboxService;

    @Override
    public void processPayment(SeatReservedEvent event) {

        if (paymentRepository.existsByBookingId(event.bookingId())) {

            log.info("Payment already exists for booking {}",
                    event.bookingId());

            return;
        }

        Payment payment = Payment.builder()
                .bookingId(event.bookingId())
                .userId(event.userId())
                .status(PaymentStatus.SUCCESS)
                .build();

        payment = paymentRepository.save(payment);

        PaymentSuccessEvent paymentSuccessEvent = new PaymentSuccessEvent(
                payment.getBookingId(),
                payment.getId(),
                LocalDateTime.now());

        outboxService.save(
                AggregateType.PAYMENT,
                payment.getId(),
                OutboxEventType.PAYMENT_SUCCESS,
                paymentSuccessEvent);

        log.info("Payment created id={}", payment.getId());

    }

}
