package com.dl.user.config;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName SyncProducerListener.java
 * @Description 消息接收者
 * @createTime 2021-06-22 22:51:00
 */

@Component
@RocketMQMessageListener(topic = "${mq.user.topic}",consumerGroup = "${mq.user.group.name}", selectorExpression = "*" ,
        //消费模式：广播
        messageModel = MessageModel.BROADCASTING,
        //消费模式：集群消费模式（默认情况）
//        messageModel = MessageModel.CLUSTERING,


        //同时接收异步传递的消息
        consumeMode = ConsumeMode.CONCURRENTLY
        //有序接收异步传递的消息
//         consumeMode = ConsumeMode.ORDERLY
)
public class PaymentListener implements RocketMQListener<MessageExt> {
    private static final Logger log = LoggerFactory.getLogger(PaymentListener.class);

    @Override
    public void onMessage(MessageExt messageExt) {

        log.info("开始接收到消息---------------------------------------");
        //1.解析消息内容
        try {
            String body = new String(messageExt.getBody(),"UTF-8");
//            User user = JSON.parseObject(body, User.class);
//            log.info("消息内容-------------：" + user);
            log.info("消息内容-------------：" + body);
        } catch (UnsupportedEncodingException e) {
            log.error("接收到消息失败 ,{}" ,e);
        }
        log.info("消息消费完成-----------------------------------------");
    }
}
