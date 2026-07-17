CREATE TABLE payments
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    booking_id BIGINT NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL,

    created_at DATETIME,

    updated_at DATETIME,

    failure_reason VARCHAR(255),

    transaction_id VARCHAR(100)
);