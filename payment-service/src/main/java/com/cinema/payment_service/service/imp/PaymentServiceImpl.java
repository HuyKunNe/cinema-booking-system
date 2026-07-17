package com.cinema.payment_service.service.imp;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxEventType;
import com.cinema.common.outbox.publisher.EventPublisher;
import com.cinema.event.EventMetadataFactory;
import com.cinema.event.PaymentFailedEvent;
import com.cinema.event.PaymentSuccessEvent;
import com.cinema.event.SeatReservedEvent;
import com.cinema.payment_service.entity.Payment;
import com.cinema.payment_service.enums.PaymentStatus;
import com.cinema.payment_service.gateway.PaymentGateway;
import com.cinema.payment_service.gateway.PaymentGatewayResult;
import com.cinema.payment_service.repository.PaymentRepository;
import com.cinema.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final EventPublisher eventPublisher;

    private final PaymentGateway paymentGateway;

    @Override
    @Transactional
    public void process(SeatReservedEvent event) {

        Payment payment = Payment.builder()
                .bookingId(event.bookingId())
                .userId(event.userId())
                .status(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

        try {

            PaymentGatewayResult result = paymentGateway.pay(payment);

            if (result.success()) {

                payment.setStatus(PaymentStatus.SUCCESS);

                payment.setTransactionId(result.transactionId());

                PaymentSuccessEvent paymentSuccessEvent = PaymentSuccessEvent.builder()
                        .paymentId(payment.getId())
                        .bookingId(payment.getBookingId())
                        .userId(payment.getUserId())
                        .transactionId(result.transactionId())
                        .paidAt(LocalDateTime.now())
                        .eventId(EventMetadataFactory.nextEventId())
                        .occurredAt(EventMetadataFactory.now())
                        .build();

                eventPublisher.publish(
                        AggregateType.PAYMENT,
                        payment.getId(),
                        OutboxEventType.PAYMENT_SUCCESS,
                        paymentSuccessEvent);

                log.info("Payment {} success", payment.getId());

            } else {

                payment.setStatus(PaymentStatus.FAILED);

                payment.setFailureReason(result.message());

                PaymentFailedEvent paymentFailedEvent = PaymentFailedEvent.builder()
                        .paymentId(payment.getId())
                        .bookingId(payment.getBookingId())
                        .userId(payment.getUserId())
                        .reason(result.message())
                        .failedAt(LocalDateTime.now())
                        .build();

                eventPublisher.publish(
                        AggregateType.PAYMENT,
                        payment.getId(),
                        OutboxEventType.PAYMENT_FAILED,
                        paymentFailedEvent);

                log.info("Payment {} failed", payment.getId());

            }

        } catch (Exception ex) {

            payment.setFailureReason(ex.getMessage());

            PaymentFailedEvent paymentFailedEvent = PaymentFailedEvent.builder()
                    .paymentId(payment.getId())
                    .bookingId(payment.getBookingId())
                    .userId(payment.getUserId())
                    .reason(ex.getMessage())
                    .failedAt(LocalDateTime.now())
                    .build();

            eventPublisher.publish(
                    AggregateType.PAYMENT,
                    payment.getId(),
                    OutboxEventType.PAYMENT_FAILED,
                    paymentFailedEvent);

            log.error("Payment exception", ex);
        }

    }

}