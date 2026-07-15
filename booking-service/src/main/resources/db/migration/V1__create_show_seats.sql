CREATE TABLE show_seat (
    id BIGINT NOT NULL AUTO_INCREMENT,
    showtime_id BIGINT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    version INT NOT NULL DEFAULT 0,
    reserved_by BIGINT NULL,
    reserved_until DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_show_seat
        PRIMARY KEY (id),

    CONSTRAINT uk_show_seat
        UNIQUE (showtime_id, seat_number)
);