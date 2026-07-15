package com.cinema.booking_service.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    private final RedissonClient redissonClient;

    public RLock getSeatLock(Long showtimeId, String seatNumber) {

        return redissonClient.getLock(
                "booking:showtime:%d:seat:%s"
                        .formatted(showtimeId, seatNumber));
    }

}