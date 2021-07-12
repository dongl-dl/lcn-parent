//package com.dongliang.lcnorder.config.rocketmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author D-L
// * @version 1.0.0
// * @ClassName MQConfig.java
// * @Description TODO
// * @createTime 2021-06-17 16:44:00
// */
//@Slf4j
//@Configuration
//public class MQConfig {
//
//    @Value("${rocketmq.producer.groupName}") //lcn-order
//    private String groupName;
//
//    @Value("${rocketmq.producer.namesrvAddr}") //192.168.10.17:9876
//    private String namesrvAddr;
//
//                                               //tpk02
//
//    @Bean
//    public DefaultMQProducer getRocketMQProducer() {
//
//        DefaultMQProducer producer;
//        producer = new DefaultMQProducer(this.groupName);
//
//        producer.setNamesrvAddr(this.namesrvAddr);
//
//        try {
//            producer.start();
//            System.out.println("start....");
//
//            log.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]", this.groupName, this.namesrvAddr));
//        } catch (MQClientException e) {
//            log.error(String.format("producer is error {}", e.getMessage(), e));
//        }
//        return producer;
//
//    }
//}
