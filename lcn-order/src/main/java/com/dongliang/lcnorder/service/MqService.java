package com.dongliang.lcnorder.service;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MqService.java
 * @Description TODO
 * @createTime 2021-06-17 16:52:00
 */
public interface MqService {

    SendResult sendMsg();

    String sendMsg01();

    String sendMsg02();

    String sendMsg03();
}
