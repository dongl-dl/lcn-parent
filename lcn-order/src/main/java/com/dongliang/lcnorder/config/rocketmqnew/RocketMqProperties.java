package com.dongliang.lcnorder.config.rocketmqnew;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RocketMqProperties.java
 * @Description 获取 rocketMq 的配置信息
 * @createTime 2021-06-18 09:22:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMqProperties {
    //nameServer 主机地址
    private String namesrvAddr;
    //超时时间
    private Integer sendMsgTimeoutMillis;
    //分组名称一般为服务名称（applicationName）
    private String groupName;
    //失败重试次数
    private Integer reconsumeTimes;
}
