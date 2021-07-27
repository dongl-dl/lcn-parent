package com.dongl.redis.config;

import com.dongl.common.lock.RedisRedLock;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedLockConfig.java
 * @Description 多机版 分布式锁
 * @createTime 2021-07-27 15:38:00
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedLockConfig {

    @Resource
    private RedissonProperties properties;


    public static RLock create(String url , String password , String key){
        Config config = new Config();
        config.useSingleServer().setAddress(url).setPassword(password);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient.getLock(key);
    }

    RedissonRedLock redissonRedLock = new RedissonRedLock(
            create(properties.getAddress() ,properties.getPassword() , "lock-key1")
    );

    @Bean
    public RedisRedLock getRedLock() {
        RedisRedLock redLock = new RedisRedLock(redissonRedLock);
        return redLock;
    }
}
