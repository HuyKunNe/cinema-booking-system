CREATE INDEX idx_show_seat_showtime
ON show_seat(showtime_id);

CREATE INDEX idx_show_seat_status
ON show_seat(status);

CREATE INDEX idx_booking_user
ON bookings(user_id);

CREATE INDEX idx_booking_showtime
ON bookings(showtime_id);

CREATE INDEX idx_booking_status
ON bookings(status);

CREATE INDEX idx_booking_seat_booking
ON booking_seats(booking_id);

CREATE INDEX idx_booking_seat_showseat
ON booking_seats(show_seat_id);