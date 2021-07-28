package com.dongl.redis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisConfigProperties.java
 * @Description 集群模式配置类
 * @createTime 2021-07-28 14:16:00
 */
@Component
@Data
public class RedisConfigProperties {
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.cluster.nodes}")
//    private List<String> nodes;
}
