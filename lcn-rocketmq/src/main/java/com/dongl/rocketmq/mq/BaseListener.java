package com.dongl.rocketmq.mq;

import com.dongl.common.mq.MsgHandlerDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName SyncProducerListener.java
 * @Description 消息接收者
 * @createTime 2021-06-22 22:51:00
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq-base.topic}",consumerGroup = "${rocketmq-base.groupName}", selectorExpression = "*" ,
        //消费模式：广播
        messageModel = MessageModel.BROADCASTING,
        //消费模式：集群消费模式（默认情况）
//        messageModel = MessageModel.CLUSTERING,


        //同时接收异步传递的消息
        consumeMode = ConsumeMode.CONCURRENTLY
        //有序接收异步传递的消息
//         consumeMode = ConsumeMode.ORDERLY
)
public class BaseListener implements RocketMQListener<MessageExt> {
    @Autowired
    private MsgHandlerDispatcher dispatcher;

    @Override
    public void onMessage(MessageExt messageExt) {
        //1.解析消息内容
        String msgContent = null;
        try {
            msgContent = new String(messageExt.getBody(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info("解析消息内容失败");
        }
        boolean dispatch = dispatcher.dispatch(msgContent);
        if(dispatch){
            log.info("消息消费成功");
        }else {
            log.info("消息消费失败");
        }
    }
}
