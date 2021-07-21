package com.dongl.common.mq;

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
    String syncSend(BaseMsg msg);

    /**
     * 发送异步消息
     * @return
     */
    String asyncSend(BaseMsg msg);

    /**
     * 发送单项消息
     * @return
     */
    String sendOneWay(BaseMsg msg);


    /**
     * 发送顺序消息
     * @return
     */
    String syncSendOrderly(BaseMsg msg);


    /**
     * 发送事务消息
     * @param msg
     * @return
     */
    String sendMessageInTransaction(BaseMsg msg);
}
