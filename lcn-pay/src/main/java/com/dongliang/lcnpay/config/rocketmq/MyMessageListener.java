package com.dongliang.lcnpay.config.rocketmq;

import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MyMessageListener.java
 * @Description TODO
 * @createTime 2021-06-17 17:00:00
 */
public class MyMessageListener implements MessageListenerConcurrently  /*MessageListenerOrderly*/{
    /**
     * 对象用于并发接收异步传递的消息
     * @param msgs
     * @param context
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt msg : msgs){
            System.out.println(new String(msg.getBody()));
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }


    /**
     * 对象用于有序接收异步传递的消息
     * @param msgs
     * @param context
     * @return
     */
//    @Override
//    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
//        for (MessageExt msg : msgs) {
//            System.out.println(new String(msg.getBody()));
//        }
//        return ConsumeOrderlyStatus.SUCCESS;
//    }
}