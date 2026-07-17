# Event-Driven Cinema Booking System

A production-style **Event-Driven Microservices**

- Event-Driven Architecture (EDA)
- Saga Pattern (Choreography)
- Transactional Outbox Pattern
- Apache Kafka
- Distributed Data Consistency
- Redis Distributed Lock (Redisson)

---

# Tech Stack

| Technology      | Version      |
| --------------- | ------------ |
| Java            | 21           |
| Spring Boot     | 3.5.4        |
| Spring Data JPA | Latest       |
| Hibernate       | Latest       |
| MySQL           | 8            |
| Flyway          | Latest       |
| Apache Kafka    | Latest       |
| Redis           | Latest       |
| Redisson        | Latest       |
| Maven           | Multi Module |
| JUnit           | 5            |

---

# Architecture

```
                 +-------------+
                 |   Client    |
                 +------+------+
                        |
                        v
               +------------------+
               |   Booking API    |
               +---------+--------+
                         |
                         v
               +------------------+
               | booking-service  |
               +------------------+
                         |
                 Database Transaction
                         |
        +----------------+----------------+
        |                                 |
        v                                 v
   bookings                      booking_seats
        |
        v
   show_seats
(lock with Redis)
        |
        v
   outbox_events (NEW)
        |
        v
 Outbox Scheduler
        |
        v
 Kafka Producer
        |
        v
===============================
 Topic: seat-reserved
===============================
```

---

# Booking Flow

```
Client
    |
    v
Booking API
    |
    v
booking-service
    |
    | Transaction
    |
    +--> bookings
    +--> booking_seats
    +--> show_seats (lock & update)
    +--> outbox_events (NEW)
    |
    v
Outbox Scheduler
    |
    v
Kafka Producer
    |
    v
Topic: seat-reserved
    |
    v
outbox_events (SENT)
```

---

# Payment Flow (Saga Pattern - Choreography)

After adding the **payment-service**, the booking process becomes an event-driven Saga.

```
Booking API
      |
      v
booking-service
      |
      | Transaction + Outbox
      |
      v
Topic: seat-reserved
      |
      v
payment-service
      |
      v
PaymentConsumer
      |
      +--> payments
      |
      +--> payment_outbox
      |
      v
Topic: payment-success
      |
      v
booking-service
      |
      v
Booking Status = CONFIRMED
```

---

# Saga Choreography

```
Booking Service
      |
      | SeatReservedEvent
      |
      v
Kafka
      |
      v
Payment Service
      |
      | PaymentSuccessEvent
      |
      v
Kafka
      |
      v
Booking Service
      |
      v
Booking Confirmed
```

Each service only reacts to events.

No central orchestrator exists.

---

# Transactional Outbox Pattern

```
┌────────────────────────────┐
│ Database Transaction        │
├────────────────────────────┤
│ bookings                    │
│ booking_seats               │
│ show_seats                  │
│ outbox_events               │
└──────────────┬──────────────┘
               │
               │ Commit
               │
               ▼
      Outbox Scheduler
               │
               ▼
        Kafka Producer
               │
               ▼
           Kafka Topic
               │
               ▼
    Mark Outbox = SENT
```

Benefits

- No lost events
- Atomic database update + event publishing
- Retry support
- Idempotent publishing

---

# Project Structure

```
cinema-booking-system
│
├── booking-service
├── payment-service
├── common-event
├── common-core
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# Run Project

Start all infrastructure:

```bash
docker compose up -d
```

Infrastructure includes:

- MySQL
- Kafka
- Kafka UI
- Redis

Run services:

```bash
mvn clean install

cd booking-service
mvn spring-boot:run

cd payment-service
mvn spring-boot:run
```

---

# Event Flow

```
Client
   |
   v
Booking API
   |
   v
booking-service
   |
   | Save Booking
   |
   v
Outbox Event
   |
   v
Kafka
   |
   v
seat-reserved
   |
   v
payment-service
   |
   | Payment Success
   |
   v
payment-success
   |
   v
booking-service
   |
   v
Booking Confirmed
```

---

# Future Improvements

- Notification Service
- Inventory Service
- Dead Letter Queue (DLQ)
- Kafka Retry Topics
- Idempotent Consumer
- Distributed Tracing
- Prometheus + Grafana
- OpenTelemetry
- Kubernetes Deployment

---

# Design Patterns

- Event-Driven Architecture
- Saga Pattern (Choreography)
- Transactional Outbox Pattern
- Repository Pattern
- Domain Events
- Retry Pattern
- Distributed Lock (Redis + Redisson)

---

# License

MIT
