event-driven microservices
run docker: docker compose up
Công nghệ sử dụng:
   Java 21
   Spring Boot 3.5.4
   Spring Data JPA
   Hibernate
   MySQL 8
   Flyway
   Redis
   Redisson
   Kafka
   JUnit 5
   Maven Multi Module

Booking flow
   Client
   |
   v
   Booking API
   |
   v
   Transaction
   |
   +--> bookings
   |
   +--> booking_seats
   |
   +--> show_seats (lock + update)
   |
   +--> outbox_events (NEW)
            |
            v
   Outbox Scheduler
            |
            v
   Kafka Producer
            |
            v
   topic: seat-reserved
            |
            v
   outbox_events (SENT)

sau khi thêm payment-service (Đây chính là Saga Pattern (Choreography))
   Booking API
         |
         |
         v
booking-service
         |
Transaction + Outbox
         |
         v
seat-reserved
   Kafka Topic
         |
         |
         v
payment-service
         |
PaymentConsumer
         |
payments table
         |
Payment Outbox
         |
         v
payment-success
   Kafka Topic
         |
         v
booking-service
         |
Booking CONFIRMED