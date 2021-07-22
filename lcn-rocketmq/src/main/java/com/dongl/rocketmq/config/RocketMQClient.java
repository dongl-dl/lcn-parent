package com.dongl.rocketmq.config;

import com.alibaba.fastjson.JSON;
import com.dongl.common.mq.BaseMsg;
import com.dongl.common.mq.MsgSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RocketMQConfig.java
 * @Description RocketMQ配置类
 * @createTime 2021-07-21 11:25:00
 */
@Slf4j
@Component
public class RocketMQClient implements MsgSender {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public String syncSend(BaseMsg msg ,  ImmutableTriple<String ,String ,String> config) throws Exception {
        Future<SendResult> submit = taskExecutor.submit(new Callable<SendResult>() {
            @Override
            public SendResult call() {
                Message message = MessageBuilder.withPayload(JSON.toJSONString(msg)).build();
                String destination = String.format("%s:%s", config.getMiddle(), config.getRight());
                SendResult sendResult = rocketMQTemplate.syncSend(destination, message);
                if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                    log.info("消息发送成功【SendStatus】 ：{}", sendResult);
                } else {
                    log.info("消息发送失败【SendStatus】 ：{}", sendResult);
                }
                return sendResult;
            }
        });
        SendResult sendResult = submit.get();
        return sendResult.getSendStatus().toString();
    }

    @Override
    public void asyncSend(BaseMsg msg , ImmutableTriple<String ,String ,String> config) {
        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Message message = MessageBuilder.withPayload(JSON.toJSONString(msg)).build();
                String destination = String.format("%s:%s",config.getMiddle() , config.getRight());
                rocketMQTemplate.asyncSend(destination, message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("消息发送成功   【发送结果】 ：{}" ,sendResult);
                    }

                    @Override
                    public void onException(Throwable e) {
                        log.info("异常：{} ,消息：{}" ,e ,message);
                        //todo 可以对异常消息进行其他操作，重新发送或者存入db
                    }
                });
            }
        });
    }

    @Override
    public String sendOneWay(BaseMsg msg , ImmutableTriple<String ,String ,String> config) {
        log.info("发送单项消息------------------------------------");
        Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(msg)).build();
        String destination = String.format("%s:%s", config.getMiddle() , config.getRight());
        rocketMQTemplate.sendOneWay(destination , message);
        return "ok";
    }

    @Override
    public String syncSendOrderly(BaseMsg msg ,ImmutableTriple<String ,String ,String> config) {
        log.info("发送顺序消息------------------------------------");
        for (int i = 0; i < 100; i++) {
            int defaultTopicQueueNums = rocketMQTemplate.getProducer().getDefaultTopicQueueNums();
            Message<String> message = MessageBuilder.withPayload("发送顺序消息" + i + "   【队列数取模】：" + String.valueOf(i%defaultTopicQueueNums)).build();
            String destination = String.format("%s:%s", config.getMiddle() , config.getRight());
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

    @Override
    public String sendMessageInTransaction(BaseMsg msg , ImmutableTriple<String ,String ,String> config) throws Exception {
        log.info("【发送消息】-------------");
        Future<TransactionSendResult> submit = taskExecutor.submit(new Callable<TransactionSendResult>() {
            @Override
            public TransactionSendResult call() {
                Message message = MessageBuilder.withPayload(JSON.toJSONString(msg)).build();
                String destination = String.format("%s:%s",config.getMiddle() , config.getRight());
                TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(config.getLeft(), destination, message, null);
                log.info("【发送状态】：{}", result.getLocalTransactionState());
                return result;
            }
        });
        LocalTransactionState localTransactionState = submit.get().getLocalTransactionState();
        return localTransactionState.toString();
    }
}
