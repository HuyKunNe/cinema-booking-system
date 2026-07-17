CREATE TABLE processed_events (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    event_id CHAR(36) NOT NULL,

    consumer_name VARCHAR(100) NOT NULL,

    processed_at DATETIME NOT NULL,

    CONSTRAINT uk_processed
        UNIQUE (
            event_id,
            consumer_name
        )

);