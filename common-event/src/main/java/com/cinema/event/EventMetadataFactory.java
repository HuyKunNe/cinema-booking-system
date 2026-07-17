package com.cinema.event;

import java.time.LocalDateTime;
import java.util.UUID;

public final class EventMetadataFactory {

    public static UUID nextEventId() {
        return UUID.randomUUID();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

}