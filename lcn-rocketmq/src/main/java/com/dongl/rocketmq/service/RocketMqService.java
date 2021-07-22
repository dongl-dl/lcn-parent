package com.dongl.rocketmq.service;

import com.dongl.common.mq.BaseMsg;
import com.dongl.common.mq.MsgSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description 消息发送
 * @createTime 2021-06-22 13:48:00
 */
@Service
@Slf4j
public class RocketMqService {

    @Autowired
    private MsgSender msgSender;

    public String sendMsg01(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) throws Exception {
        return msgSender.syncSend( msg ,mqConfig);
    }

    public void sendMsg02(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        msgSender.asyncSend(msg , mqConfig);
    }

    public String sendOneWayMsg(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        return msgSender.sendOneWay(msg , mqConfig);
    }

    public String sendOrderlyMsg(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        return msgSender.syncSendOrderly(msg , mqConfig);
    }

    public String sendTransactionMessage(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) throws Exception {
        return msgSender.sendMessageInTransaction(msg , mqConfig);
    }

    public String updateUserInfo() {
        int i = 1/0;
        return "OK";
    }
}
