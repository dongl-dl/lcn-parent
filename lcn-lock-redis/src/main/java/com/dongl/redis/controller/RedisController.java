package com.dongl.redis.controller;

import com.dongl.common.lock.RedisLock;
import com.dongl.common.lock.RedisRedLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description TODO
 * @createTime 2021-07-27 15:50:00
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisRedLock redLock;

    @Resource
    private RedisLock redisLock;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    int count = 0;

    @GetMapping("/testLock")
    public void testLock() throws ExecutionException, InterruptedException {

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        redisLock.lock();
                        setCount();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        redisLock.unlock();
                    }
                }
            }).start();
        }
    }

    public void setCount( ) {
        count ++;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
