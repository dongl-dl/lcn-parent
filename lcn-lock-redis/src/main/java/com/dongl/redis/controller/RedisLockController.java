package com.dongl.redis.controller;

import com.dongl.redis.lock.RedissonRedLockClient;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisLockController.java
 * @Description TODO
 * @createTime 2021-07-28 14:19:00
 */
@RestController
@RequestMapping("/test")
public class RedisLockController {
    private static String product1Count = "product1Count";//商品1的数量key
    private static String lockKey = "testLockKey";//分布式锁的key
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonRedLockClient redLock;

    /**
     * 初始化设置商品数量
     *
     * @return
     */
    @RequestMapping("/set-product-count")
    public String setValue() {
        redisTemplate.opsForValue().set(product1Count, "10");
        return "success";
    }

    /**
     * 模拟秒杀抢购，并发多个请求过来，查看是否出现超卖
     * 不加锁
     * @return
     */
    @RequestMapping("/panic-buying")
    public String panicBuying() {
        String flag = "success";
        try {
            int stock = Integer.parseInt(redisTemplate.opsForValue().get(product1Count).toString());
            if (stock > 0) {
                redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
            } else {
                flag = "fail";
            }
            System.out.println("result:" + flag + "-----"+stock);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 模拟秒杀抢购，并发多个请求过来，查看是否出现超卖
     *
     * 加锁
     * waitTime:获取锁等待时间 , leaseTime：锁的过期时间
     * @return
     */
    @RequestMapping("/panic-buying-lock")
    public String panicBuyingLock() {
        String flag = "success";
        try {
//            redLock.lockAsync(lockKey , 5 , TimeUnit.SECONDS);
//            redLock.lock(lockKey ,30, TimeUnit.SECONDS); //设置60秒自动释放锁  （默认是30秒自动过期）
            boolean result = redLock.tryLockAsync(lockKey, 100, 5, TimeUnit.SECONDS);
            if (/*true*/ result) {
                int stock = Integer.parseInt(redisTemplate.opsForValue().get(product1Count).toString());
                if (stock > 0) {
                    redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
                } else {
                    flag = "fail";
                }
                System.out.println("result:" + flag + "-----"+stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redLock.unlock(lockKey); //释放锁
        }
        return flag;
    }
}