package com.cinema.event;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SeatReservedEvent(

        Long bookingId,

        Long userId,

        Long showtimeId,

        List<String> seatNumbers,

        LocalDateTime createdAt

) implements Serializable {

}