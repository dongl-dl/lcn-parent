package com.dongl.redis.controller;

import com.dongl.redis.lock.RedissonRedLockClient;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
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
    @Autowired
    private Redisson redisson;

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

    /**
     * 使用redis 的setNx (幼儿园级别的分布式锁)
     * @return
     */
    @RequestMapping("/deduct_stock")
    public String deductStock(){
        String flag = "success";
        String lockKey = "P_001";
        String valueId = UUID.randomUUID().toString();
        try {
            //Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, "dongl"); //相当于jedis.setnx(key , value)
            //redisTemplate.expire(lockKey , 10 , TimeUnit.SECONDS); //不能保证原子性 ,解决办法：用一行命令设置过期时间

            //如果第一个线程执行到10秒 锁自动过期，第二个线程执行到5秒 线程二加的锁被线程一释放，
            // 这样周而复始及其可能出现前一个线程加的锁立刻就被后面的线程给释放，导致锁永久失效。
            //解决办法：value值设置唯一值 ， 释放锁之前判断是否为自己线程假的锁。
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, valueId, 10, TimeUnit.SECONDS);

            //这里会存在过期时间大小的设置问题， 解决办法：开启守护线程强行续命

            if (!result) {
                return "error";
            }
            //如果程序执行到这里 redis 服务宕机，而且这把锁没有过期时间，这把锁就永远存在，也就是所谓的死锁。
            int stock = Integer.parseInt(redisTemplate.opsForValue().get(product1Count)); //jedis.get(product1Count)
            if (stock > 0) {
                redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
            } else {
                flag = "fail";
            }
            System.out.println("result:" + flag + "-----" + stock);
        }finally {
            if(valueId.equals(redisTemplate.opsForValue().get(lockKey))){
                //释放锁
                redisTemplate.delete(lockKey);
            }
        }
        return flag;
    }

    /**
     * 使用redisson单机模式实现分布式锁
     * @return
     */
    @RequestMapping("/deduct_stock_redisson")
    public String deductStockRedisson(){
        String flag = "success";
        String lockKey = "P_001";

        RLock lock = redisson.getLock(lockKey);
        lock.lock(30 , TimeUnit.SECONDS);
        try {
            int stock = Integer.parseInt(redisTemplate.opsForValue().get(product1Count)); //jedis.get(product1Count)
            if (stock > 0) {
                redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
            } else {
                flag = "fail";
            }
            System.out.println("result:" + flag + "-----" + stock);
        }finally {
            lock.unlock();
        }
        return flag;
    }
}