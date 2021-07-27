package com.dongl.redis.config;

import com.dongl.common.lock.RedisLock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName Redisson.java
 * @Description 单机版 分布式锁
 * @createTime 2021-07-27 15:37:00
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfig {

    @Autowired
    private RedissonProperties properties;

    private static RedissonClient create(String url, String password){
        Config config = new Config();
        config.useSingleServer().setAddress(url).setPassword(password);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

    @Bean
    public RedisLock getLock() {
        RedissonClient redissonClient = create(properties.getAddress() , properties.getPassword());
        final RedisLock redisLock = new RedisLock(redissonClient ,"lock_key");
        return redisLock;
    }
}
