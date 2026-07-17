package com.cinema.event;

import java.util.List;

import lombok.Builder;

@Builder
public record SeatReleasedEvent(

        Long bookingId,

        Long showtimeId,

        List<String> seatNumbers

) {
}