CREATE TABLE outbox_events
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    aggregate_type VARCHAR(50),

    aggregate_id BIGINT,

    event_type VARCHAR(100),

    payload LONGTEXT,

    status VARCHAR(20),

    processed_at DATETIME,

    created_at DATETIME,

    updated_at DATETIME
);