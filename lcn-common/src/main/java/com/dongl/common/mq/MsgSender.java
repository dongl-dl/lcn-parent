package com.dongl.common.mq;

import org.apache.commons.lang3.tuple.ImmutableTriple;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MsgSender.java
 * @Description 不同消息发送
 * @createTime 2021-07-21 11:50:00
 */
public interface MsgSender {

    /**
     * 发送同步消息
     * @return
     */
    String syncSend(BaseMsg msg , ImmutableTriple<String ,String ,String> config) throws Exception;

    /**
     * 发送异步消息
     * @return
     */
    void asyncSend(BaseMsg msg , ImmutableTriple<String ,String ,String> config);

    /**
     * 发送单项消息
     * @return
     */
    String sendOneWay(BaseMsg msg , ImmutableTriple<String ,String ,String> config);


    /**
     * 发送顺序消息
     * @return
     */
    String syncSendOrderly(BaseMsg msg , ImmutableTriple<String ,String ,String> config);


    /**
     * 发送事务消息
     * @param msg
     * @return
     */
    String sendMessageInTransaction(BaseMsg msg , ImmutableTriple<String ,String ,String> config) throws Exception;
}
