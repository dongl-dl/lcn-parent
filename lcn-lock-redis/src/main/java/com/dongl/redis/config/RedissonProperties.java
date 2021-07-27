package com.dongl.redis.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedissonProperties.java
 * @Description 获取 Redisson 配置信息
 * @createTime 2021-07-26 15:15:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
    private int timeout;

    private String address;

    private String password;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize = 10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

    private int database;

}
