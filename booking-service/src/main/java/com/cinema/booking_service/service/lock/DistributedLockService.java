package com.cinema.booking_service.service.lock;

import java.util.function.Supplier;

public interface DistributedLockService {

    <T> T executeWithLock(
            String key,
            Supplier<T> action);

}