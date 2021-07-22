package com.dl.user.config.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RocketMQConfig.java
 * @Description rocketMQ配置类
 * @createTime 2021-07-22 09:45:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq-base")
public class RocketMQConfig {

    /**-------------------------用户信息mq配置----------------------------*/
    private String topic;
    private String groupName;
    private String tag;


    /**-------------------------删除用户mq配置-----------------------------*/
    private String userDeleteTopic;
    private String userDeleteGroupName;
    private String userDeletetag;
}
