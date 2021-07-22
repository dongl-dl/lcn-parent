package com.dongl.rocketmq.service;

import com.dongl.common.mq.BaseMsg;
import com.dongl.common.mq.MsgSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String sendMsg01(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        String s = msgSender.syncSend( msg ,mqConfig);
        return s;
    }

    public String sendMsg02(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        String s = msgSender.asyncSend(msg , mqConfig);
        return s;
    }

    public String sendOneWayMsg(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        String s = msgSender.sendOneWay(msg , mqConfig);
        return s;
    }

    public String sendOrderlyMsg(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        String s = msgSender.syncSendOrderly(msg , mqConfig);
        return s;
    }

    public String sendTransactionMessage(BaseMsg msg , ImmutableTriple<String , String, String> mqConfig) {
        String s = msgSender.sendMessageInTransaction(msg , mqConfig);
        return s;
    }

    /**
     *
     * @return
     */
    public String updateUserInfo(){
        log.info("操作用户信息------------------------------------");
        try {
//            int count = 1/0;
        }catch (Exception e) {
            log.info("操作用户信息失败---------------");
            return "fail";
        }
        return "ok";
    }
}
