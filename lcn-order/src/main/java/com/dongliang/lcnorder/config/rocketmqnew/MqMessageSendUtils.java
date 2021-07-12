package com.dongliang.lcnorder.config.rocketmqnew;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MqMessageSendUtils.java
 * @Description Mq 消息发送工具类
 * @createTime 2021-06-18 09:26:00
 */
@Component
@Slf4j
public class MqMessageSendUtils {
    private static DefaultMQProducer rocketMqProducer = ApplicationContextHelper.getBean(DefaultMQProducer.class);

    /**
     * 发送消息（同步消息）
     * @param topic
     * @param tag
     * @param body
     * @return
     */
    public static <T> boolean sendSyncMessage(String topic, String tag, T body) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        log.info(String.format("sendSyncMessage is start ! topic:[%s],tag:[%s]", topic, tag));
        try {
            SendResult sendResult = rocketMqProducer.send(message);
            //发送状态
            SendStatus status = sendResult.getSendStatus();
            if(status == SendStatus.SEND_OK){
                log.info("消息发送成功" + sendResult);
                return true;
            }else {
                log.info("消息发送失败" + sendResult);
                return false;
            }
        } catch (Exception e) {
            log.error("消息发送失败", e.getMessage());
            return false;
        }
    }

    /**
     * 发送消息（异步消息）
     * @param topic
     * @param tag
     * @param body
     */
    public static <T>  void sendAsyncMessage(String topic, String tag, T body) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        log.info(String.format("sendAsyncMessage is start ! topic:[%s],tag:[%s]", topic, tag));
        try {
            rocketMqProducer.send(message, new SendCallback() {
                /**
                 * 发送成功回调函数
                 * @param sendResult
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    //发送状态
                    SendStatus status = sendResult.getSendStatus();
                    if (status == SendStatus.SEND_OK) {
                        log.info("消息发送成功" + sendResult);
                    } else {
                        log.warn("消息发送失败" + sendResult);
                    }
                }

                /**
                 * 发送失败回调函数
                 * @param e
                 */
                @Override
                public void onException(Throwable e) {
                    log.warn("消息发送失败");
                }
            });
        } catch (Exception e) {
           log.error("消息发送失败");
        }
    }

    /**
     * 发送消息（单向消息）
     * @param topic
     * @param tag
     * @param body
     * @return
     */
    public static <T>  boolean sendOneWayMessage(String topic, String tag, T body) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        log.info(String.format("sendOneWayMessage is start ! topic:[%s],tag:[%s]", topic, tag));
        try {
            rocketMqProducer.sendOneway(message);
            log.info("消息发送成功");
            return true;
        } catch (Exception e) {
            log.error("消息发送失败"+e.getMessage());
            return false;
        }
    }

    /**
     * 批量发送消息
     * @param topic
     * @param tag
     * @param list
     * @return
     */
    public static <T>  boolean sendBatchMessage(String topic, String tag, List<T> list) {
        log.info(String.format("sendBatchMessage is start ! topic:[%s],tag:[%s]", topic, tag));
        List<Message> messageList = new ArrayList<>();
        list.forEach(msg ->{
            Message message = new Message(topic, tag, JSON.toJSONBytes(msg));
            messageList.add(message);
        });
        try {
            SendResult result = rocketMqProducer.send(messageList);
            SendStatus sendStatus = result.getSendStatus();
            if(sendStatus == SendStatus.SEND_OK){
                log.info("消息发送成功" + sendStatus);
                return true;
            }else {
                log.info("消息发送失败" + sendStatus);
                return false;
            }
        } catch (Exception e) {
            log.error("消息发送失败"+e.getMessage());
            return false;
        }
    }


    /**
     * 发送普通延时消息
     *
     * @param topic
     * @param tag
     * @param body
     * @return
     */
    public static <T>  boolean sendDelayMessage(String topic, String tag, T body) {
        log.info(String.format("sendDelayMessage is start ! topic:[%s],tag:[%s]", topic, tag));
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        // 默认延时 10s
        // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(3);
        try {
            SendResult sendResult = rocketMqProducer.send(message);
            SendStatus sendStatus = sendResult.getSendStatus();
            if(sendStatus == SendStatus.SEND_OK){
                log.info("延时消息发送成功" + sendResult);
                return true;
            }else {
                log.info("延时消息发送失败" + sendResult);
                return false;
            }
        } catch (Exception e) {
            log.error("mq 延时消息发送失败", e.getMessage());
            return false;
        }
    }

//    public static <T>  boolean sendTransactionMessage(String topic, String tag, T body ,TransactionListener transactionListener) throws MQClientException {
//        log.info(String.format("sendTransactionMessage is start ! topic:[%s],tag:[%s]", topic, tag));
//        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
//        SendResult result = transactionMQProducer.sendMessageInTransaction(message, null);
//        //发送状态
//        SendStatus status = result.getSendStatus();
//        System.out.println("发送结果:" + result);
//        return true;
//    }
}