package com.dl.user.config;

import com.dl.user.service.RocketMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName SyncProducerListener.java
 * @Description 事务消息
 *              Half Message：预处理消息，当broker收到此类消息后，会存储到RMQ_SYS_TRANS_HALF_TOPIC的消息消费队列中
 *
 *              检查事务状态：Broker会开启一个定时任务，消费RMQ_SYS_TRANS_HALF_TOPIC队列中的消息，每次执行任务会向消息发送者确认事务执行状态（提交、回滚、未知），如果是未知，等待下一次回调。
 *
 *              超时：如果超过回查次数，默认回滚消息
 *
 * @createTime 2021-06-22 14:51:00
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "lcn-user01")
public class SyncProducerListener implements RocketMQLocalTransactionListener {
    private ConcurrentHashMap<Integer, RocketMQLocalTransactionState> map=new ConcurrentHashMap<>();

    @Autowired
    private RocketMqService rocketMqService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        map.put(message.hashCode(),RocketMQLocalTransactionState.UNKNOWN);

        String result = rocketMqService.updateUserInfo();

        if(!result.equalsIgnoreCase("OK")){
            System.out.println("本地事务出错，回滚事务消息--------");
            map.put(message.hashCode(),RocketMQLocalTransactionState.ROLLBACK);
        }else {
            map.put(message.hashCode(),RocketMQLocalTransactionState.COMMIT);
        }
        log.info(map.get(message.hashCode()).toString());
        return map.get(message.hashCode());
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        System.out.println("没有获得消息ack  -----  进行消息回查   消息的Tag:" + message);
        return map.get(message.hashCode());
    }
}
