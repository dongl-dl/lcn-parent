package com.dongliang.lcnorder.config.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedissonProperties.java
 * @Description 获取 Redisson 配置信息
 * @createTime 2021-06-04 23:40:00
 */

@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    private int timeout = 3000;

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
