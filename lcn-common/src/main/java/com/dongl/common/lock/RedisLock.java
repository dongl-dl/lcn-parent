package com.dongl.common.lock;

import com.dongl.common.lock.AbstractLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisLock.java
 * @Description 封装单机版锁
 * @createTime 2021-07-27 13:57:00
 */
public class RedisLock extends AbstractLock {
    private RedissonClient client;
    private String key;

    public RedisLock(RedissonClient client, String key) {
        this.client = client;
        this.key = key;
    }


    @Override
    public void lock() {
        client.getLock(key).lock();
    }

    public void lock(long time, TimeUnit unit) {
        client.getLock(key).lock(time, unit);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        client.getLock(key).lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return  client.getLock(key).tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
       return client.getLock(key).tryLock(time, unit);
    }

    @Override
    public void unlock() {
        client.getLock(key).unlock();
    }

    @Override
    public Condition newCondition() {
       return client.getLock(key).newCondition();
    }
}
