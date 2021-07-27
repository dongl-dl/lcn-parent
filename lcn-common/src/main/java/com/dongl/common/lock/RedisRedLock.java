package com.dongl.common.lock;

import org.redisson.RedissonRedLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisRedLock.java
 * @Description 封装单机版锁
 * @createTime 2021-07-27 14:13:00
 */
public class RedisRedLock extends AbstractLock{

    private RedissonRedLock redLock;

    public RedisRedLock(){}

    public RedisRedLock(RedissonRedLock redLock) {
        this.redLock = redLock;
    }

    @Override
    public void lock() {
        redLock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        redLock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return redLock.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return redLock.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        redLock.unlock();
    }

    @Override
    public Condition newCondition() {
        return redLock.newCondition();
    }
}
