package com.cinema.booking_service.service.lock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedissonDistributedLockService
        implements DistributedLockService {

    private final RedissonClient redissonClient;

    @Override
    public <T> T executeWithLock(String key, Supplier<T> action) {

        RLock lock = redissonClient.getLock(key);

        try {

            boolean locked = lock.tryLock(5, 10, TimeUnit.SECONDS);

            if (!locked) {
                throw new RuntimeException("Cannot acquire lock");
            }

            return action.get();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            throw new RuntimeException(e);

        } finally {

            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}