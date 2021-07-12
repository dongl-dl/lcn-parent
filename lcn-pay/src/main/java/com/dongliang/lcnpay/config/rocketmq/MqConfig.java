package com.dongliang.lcnpay.config.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MqConfig.java
 * @Description TODO
 * @createTime 2021-06-17 16:58:00
 */
@Configuration
public class MqConfig {
    @Value("${rocketmq.consumer.namesrvAddr}") //192.168.10.17:9876
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}") //lcn-pay
    private String groupName;
    @Value("${rocketmq.consumer.topics}") //tpk02
    private String topics;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topics, "*");
        // 修改消费模式，默认是集群消费模式，修改为广播消费模式
//        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.registerMessageListener(new MyMessageListener() );
        consumer.start();

        return consumer;
    }
}
