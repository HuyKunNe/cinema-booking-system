package com.cinema.booking_service.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinema.booking_service.entity.ShowSeat;
import com.cinema.booking_service.repository.ShowSeatRepository;
import com.cinema.booking_service.service.ShowSeatService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowSeatServiceImp implements ShowSeatService {

    private final ShowSeatRepository showSeatRepository;

    @Override
    public List<ShowSeat> lockSeats(Long showtimeId, List<String> seatNumbers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void releaseSeats(Long bookingId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void markBooked(Long bookingId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
