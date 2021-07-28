package com.dongl.redis.lock;

import com.dongl.common.lock.DistributedLocker;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedissonRedLock.java
 * @Description TODO
 * @createTime 2021-07-28 17:24:00
 */
@Component
public class RedissonRedLockClient implements DistributedLocker {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
    }

    @Override
    public void lock(String lockKey, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout , TimeUnit.SECONDS);
    }

    @Override
    public void lock(String lockKey, long timeout , TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout , unit);
    }

    @Override
    public void lockAsync(String lockKey, long timeout , TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lockAsync(timeout , unit);
    }

    @Override
    public boolean tryLock(String lockKey, long waitTime, long leaseTime , TimeUnit unit) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockKey);
        boolean tryLock = lock.tryLock(waitTime, leaseTime, unit);
        return tryLock;
    }

    @Override
    public boolean tryLock(String lockKey, long timeout , TimeUnit unit) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockKey);
        boolean tryLock = lock.tryLock(timeout ,unit);
        return tryLock;
    }

    @Override
    public boolean tryLockAsync(String lockKey, long waitTime, long leaseTime , TimeUnit unit) throws ExecutionException, InterruptedException {
        RLock lock = redissonClient.getLock(lockKey);
        Future<Boolean> future = lock.tryLockAsync(waitTime, leaseTime, unit);
        return future.get();
    }
    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

}
