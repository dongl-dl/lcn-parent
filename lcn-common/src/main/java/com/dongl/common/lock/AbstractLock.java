package com.dongl.common.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName AbstractLock.java
 * @Description TODO
 * @createTime 2021-07-27 13:59:00
 */
public abstract class AbstractLock implements Lock {

    @Override
    public void lock() {
        throw new RuntimeException("不支持操作");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new RuntimeException("不支持操作");
    }

    @Override
    public boolean tryLock() {
        throw new RuntimeException("不支持操作");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new RuntimeException("不支持操作");
    }

    @Override
    public void unlock() {
        throw new RuntimeException("不支持操作");
    }

    @Override
    public Condition newCondition() {
        throw new RuntimeException("不支持操作");
    }
}
