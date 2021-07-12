package com.dongliang.lcnorder.config.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedissonAutoConfiguration.java
 * @Description Redisson 自动配置 初始化bean
 * @createTime 2021-06-04 23:40:00
 */

@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties properties;

    @Bean(name = "redissonClient")
    @ConditionalOnProperty(name = "redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        config.useSingleServer().setAddress(properties.getAddress())
                .setPassword(properties.getPassword())
                .setTimeout(properties.getTimeout())
                .setDatabase(properties.getDatabase())
                .setConnectionPoolSize(properties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize())
                .setPingConnectionInterval(60);
        return Redisson.create(config);
    }

    @Bean
    RedissonDistributedLock distributedLocker() {
        RedissonDistributedLock distributedLocker = new RedissonDistributedLock();
        distributedLocker.setRedissonClient(redissonSingle());
        return distributedLocker;
    }
}
