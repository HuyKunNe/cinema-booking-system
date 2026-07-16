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
   HTTP
   ↓
   Controller
   ↓
   Redis Lock
   ↓
   Transaction
   ↓
   SELECT FOR UPDATE
   ↓
   Kiểm tra ghế
   ↓
   Booking
   ↓
   BookingSeat
   ↓
   Update ShowSeat
   ↓
   Commit
