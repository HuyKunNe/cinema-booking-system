package com.cinema.booking_service.service.lock;

import com.cinema.booking_service.exception.BookingException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class RedissonDistributedLockService
        implements DistributedLockService {

    private final RedissonClient redissonClient;

    @Override
    public <T> T executeWithLock(
            String key,
            Supplier<T> action) {

        RLock lock = redissonClient.getLock(key);

        boolean locked = false;

        try {

            locked = lock.tryLock(
                    5,
                    30,
                    TimeUnit.SECONDS);

            if (!locked) {

                throw new BookingException(
                        "System busy. Please try again");
            }

            return action.get();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();

            throw new BookingException(
                    "Cannot acquire lock");

        } finally {

            if (locked &&
                    lock.isHeldByCurrentThread()) {

                lock.unlock();

            }
        }
    }
}