package com.dongliang.lcnorder.config.rocketmqnew;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RocketMQConfig.java
 * @Description 初始化生产者（producer）
 * @createTime 2021-06-18 09:24:00
 */
@Component
public class RocketMQConfig {
    @Autowired
    private RocketMqProperties mqProperties;

    @Bean
    public DefaultMQProducer defaultProducer() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(mqProperties.getGroupName());
        // 设置NameServer的地址
        producer.setNamesrvAddr(mqProperties.getNamesrvAddr());
        // 设置发送消息超时时间
        producer.setSendMsgTimeout(mqProperties.getSendMsgTimeoutMillis());
        // 设置重试次数
        producer.setRetryTimesWhenSendFailed(mqProperties.getReconsumeTimes());
        // 启动Producer实例
        producer.start();
        // 返回初始化好的生产者实例
        return producer;
    }


//    @Bean
//    public TransactionMQProducer transactionProducer() throws Exception {
//        // 实例化消息生产者Producer
//        TransactionMQProducer producer = new TransactionMQProducer("groupTransaction");
//        // 设置NameServer的地址
//        producer.setNamesrvAddr(mqProperties.getNamesrvAddr());
//        // 设置发送消息超时时间
//        producer.setSendMsgTimeout(mqProperties.getSendMsgTimeoutMillis());
//        // 设置重试次数
//        producer.setRetryTimesWhenSendFailed(mqProperties.getReconsumeTimes());
//        // 启动Producer实例
//        producer.start();
//        // 返回初始化好的生产者实例
//        return producer;
//    }
}
