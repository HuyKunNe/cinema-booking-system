package com.cinema.booking_service.dto.response;

import com.cinema.booking_service.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReserveSeatResponse {

        private Long bookingId;

        private BookingStatus status;

        private String message;

}