package com.dongliang.lcnorder.util.threadpooltask;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName VisiableThreadPoolTaskExecutor.java
 * @Description TODO
 * @createTime 2021-05-17 13:52:00
 */
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(task);
    }
}
