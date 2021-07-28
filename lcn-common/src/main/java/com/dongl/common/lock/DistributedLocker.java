package com.dongl.common.lock;

import org.redisson.api.RLock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName DistributedLocker.java
 * @Description TODO
 * @createTime 2021-07-28 11:29:00
 */
public interface DistributedLocker {

    void lock(String lockKey);

    void lock(String lockKey, long timeout);

    void lock(String lockKey, long timeout , TimeUnit unit);

    public void lockAsync(String lockKey, long timeout , TimeUnit unit);

    boolean tryLock(String lockKey, long waitTime, long leaseTime , TimeUnit unit) throws InterruptedException;

    boolean tryLock(String lockKey, long timeout , TimeUnit unit) throws InterruptedException;

    public boolean tryLockAsync(String lockKey, long waitTime, long leaseTime , TimeUnit unit) throws ExecutionException, InterruptedException;

    void unlock(String lockKey);
}
