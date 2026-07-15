|
Client
   │
   ▼
Controller
   │
   ▼
BookingService
   │
   ▼
Redis Lock
   │
   ▼
BEGIN TRANSACTION
   │
   ▼
SELECT ... FOR UPDATE
   │
   ▼
Kiểm tra ghế
   │
   ▼
Update ShowSeat
   │
   ▼
Insert Booking
   │
   ▼
Insert BookingSeat
   │
   ▼
COMMIT
   │
   ▼
Kafka Producer