package com.dongl.common.lock;

import com.dongl.common.lock.AbstractLock;
import org.redisson.RedissonRedLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisRedLock.java
 * @Description 封装红锁
 * @createTime 2021-07-27 14:13:00
 */
public class RedisRedLock extends AbstractLock {

    private RedissonRedLock redLock;

    public RedisRedLock(){}

    public RedisRedLock(RedissonRedLock redLock) {
        this.redLock = redLock;
    }

    @Override
    public void lock() {
        redLock.lock();
    }

    public void lock(long time, TimeUnit unit) {
        redLock.lock(time, unit);
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
