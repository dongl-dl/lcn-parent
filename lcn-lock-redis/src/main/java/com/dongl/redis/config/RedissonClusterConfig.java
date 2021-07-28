package com.dongl.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedissonCluster.java
 * @Description 集群模式 redisson 分布式锁   这里为了测试先开启了单机版   如果想使用集群模式 需要注掉集群模式对应的代码以及配置文件
 * @createTime 2021-07-28 15:29:00
 */
@Configuration
public class RedissonClusterConfig {
//    @Autowired
//    private RedisConfigProperties redisConfigProperties;
//
//    @Bean
//    public RedissonClient redissonClient() {
//        //redisson集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
//        List<String> clusterNodes = new ArrayList<>();
//        for (int i = 0; i < redisConfigProperties.getNodes().size(); i++) {
//            clusterNodes.add("redis://" + redisConfigProperties.getNodes().get(i));
//        }
//        Config config = new Config();
//        // 添加集群地址
//        ClusterServersConfig clusterServersConfig = config.useClusterServers().addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
//        // 设置密码
//        clusterServersConfig.setPassword(redisConfigProperties.getPassword());
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }
}
