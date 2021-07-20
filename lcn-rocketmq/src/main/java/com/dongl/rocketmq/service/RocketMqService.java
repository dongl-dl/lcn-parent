package com.dongl.rocketmq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;


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

    @Value("${mq.user.topic}")
    private String topic;
    @Value("${mq.user.tag}")
    private String tag;
    @Value("${mq.user.key}")
    private String key;
    @Value("${mq.user.group.name}")
    private String group;


    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息
     * @return
     */
    public String sendMsg(){
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                SendResult result = null;
                try {
                    org.apache.rocketmq.common.message.Message message = new org.apache.rocketmq.common.message.Message(topic, tag, key, "rocketMQTemplate".getBytes());
                    result = rocketMQTemplate.getProducer().send(message);
                }catch (Exception e){
                    log.error("发送消息失败--------");
                }
                if(result.getSendStatus().equals(SendStatus.SEND_OK)){
                    log.info("消息发送成功【SendStatus】 ：{}" ,result);
                }
            }
        });
        return "ok";
    }

    /**
     * 发送同步消息
     * @return
     */
    public String sendMsg01(){
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Message message = MessageBuilder.withPayload("发送同步消息").build();
                String destination = String.format("%s:%s",topic ,tag);
                SendResult sendResult = rocketMQTemplate.syncSend(destination, message);
                if(sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
                    log.info("消息发送成功【SendStatus】 ：{}" ,sendResult);
                }else {
                    log.info("消息发送失败【SendStatus】 ：{}" ,sendResult);
                }
            }
        });
        return "ok";
    }

    /**
     * 发送异步消息
     * @return
     */
    public String sendMsg02(){
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Message message = MessageBuilder.withPayload("发送异步消息").build();
                String destination = String.format("%s:%s",topic ,tag);
                rocketMQTemplate.asyncSend(destination, message, new SendCallback() {
                    /**
                     * 发送成功回调函数
                     * @param sendResult
                     */
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("消息发送成功   【发送结果】 ：{}" ,sendResult);
                    }
                    /**
                     * 发送失败回调函数
                     * @param e
                     */
                    @Override
                    public void onException(Throwable e) {
                        log.info("异常：{} ,消息：{}" ,e ,message);
                        //todo 可以对异常消息进行其他操作，重新发送或者存入db
                    }
                });
            }
        });
        return "ok";
    }

    /**
     * 发送单项消息
     * @return
     */
    public String sendOneWayMsg(){
        log.info("发送单项消息------------------------------------");
        Message<String> message = MessageBuilder.withPayload("发送单项消息").build();
        String destination = String.format("%s:%s", topic, tag);
        rocketMQTemplate.sendOneWay(destination , message);
        return "ok";
    }


    /**
     * 发送顺序消息
     * @return
     */
    public String sendOrderlyMsg(){
        log.info("发送顺序消息------------------------------------");
        for (int i = 0; i < 100; i++) {
            int defaultTopicQueueNums = rocketMQTemplate.getProducer().getDefaultTopicQueueNums();
            Message<String> message = MessageBuilder.withPayload("发送顺序消息" + i + "   【队列数取模】：" + String.valueOf(i%defaultTopicQueueNums)).build();
            String destination = String.format("%s:%s", topic, tag);
            //根据i对topic中queue的数量取模后的值   放入对应的队列中  只能保证对应
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(destination, message, String.valueOf(i%defaultTopicQueueNums));
            if(sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
                log.info("发送顺序消息成功 【消息内容】 ：{}" ,sendResult);
            }else {
                log.info("发送顺序消息失败--------------------");
            }
        }
        return "ok";
    }


    /**
     * 发送事务消息
     * @param msgStr
     * @return
     */
    public String sendTransactionMessage(String msgStr){
        log.info("【发送消息】-------------");
        Future<TransactionSendResult> submit = threadPoolTaskExecutor.submit(new Callable<TransactionSendResult>() {
            @Override
            public TransactionSendResult call() {
                Message message = MessageBuilder.withPayload(msgStr).build();
                TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("lcn-user01", topic, message, tag);
                log.info("【发送状态】：{}", result.getLocalTransactionState());
                return result;
            }
        });
        LocalTransactionState localTransactionState = null;
        try {
            localTransactionState = submit.get().getLocalTransactionState();
        } catch (Exception e) {
            log.error("获取消息发送状态失败------------------");
        }
        return localTransactionState.toString();
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
