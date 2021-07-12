package com.dongliang.lcnorder.service.impl;

import com.dongliang.lcnorder.config.rocketmqnew.MqMessageSendUtils;
import com.dongliang.lcnorder.service.MqService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MqService.java
 * @Description TODO
 * @createTime 2021-06-17 16:53:00
 */
@Service
public class MqServiceImpl implements MqService {
    @Autowired
    DefaultMQProducer producer;

    @Override
    public SendResult sendMsg() {
        for (int i = 0; i < 1; i++) {
            Message message = new Message("tpk02", "xx".getBytes());

            try {
                return producer.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public String sendMsg01() {
        List<String> list = new ArrayList<>();
        list.add("消息队列工具方法01");
        list.add("消息队列工具方法02");
        list.add("消息队列工具方法03");
        list.add("消息队列工具方法04");
        MqMessageSendUtils.sendBatchMessage("tpk02" , "tag02" ,list);
        return "OK";
    }

    @Override
    public String sendMsg02() {
        return "ok";
    }

    @Override
    public String sendMsg03() {
        return null;
    }

}
