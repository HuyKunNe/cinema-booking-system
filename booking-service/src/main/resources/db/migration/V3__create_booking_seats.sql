CREATE TABLE booking_seats (
    id BIGINT NOT NULL AUTO_INCREMENT,

    booking_id BIGINT NOT NULL,

    show_seat_id BIGINT NOT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_booking_seats
        PRIMARY KEY (id),

    CONSTRAINT fk_booking_seat_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(id),

    CONSTRAINT fk_booking_seat_show_seat
        FOREIGN KEY (show_seat_id)
        REFERENCES show_seat(id),

    CONSTRAINT uk_booking_show_seat
        UNIQUE (booking_id, show_seat_id)
);