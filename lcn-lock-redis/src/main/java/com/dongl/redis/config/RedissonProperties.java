package com.dongl.redis.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedissonProperties.java
 * @Description 获取 Redisson 配置信息
 * redisson:
 *   address: redis://127.0.0.1:6379
 *   password: 961230
 *   database: 1
 *   timeout: 10000
 * @createTime 2021-07-26 15:15:00
 */
@Data
@Component
public class RedissonProperties {
    @Value("${redisson.timeout}")
    private int timeout;
    @Value("${redisson.address}")
    private String address;
    @Value("${redisson.password}")
    private String password;
    @Value("${redisson.database}")
    private int database;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize = 10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;


}
