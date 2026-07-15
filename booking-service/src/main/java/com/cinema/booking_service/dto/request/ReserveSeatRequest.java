package com.cinema.booking_service.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ReserveSeatRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long showtimeId;

    @NotEmpty
    private List<String> seatNumbers;

}