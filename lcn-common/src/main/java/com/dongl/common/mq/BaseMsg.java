package com.dongl.common.mq;


import lombok.Data;

/**
 * MQ消息体基类
 */
@Data
public abstract class BaseMsg {

    /**
     * 消息处理器标记，用于消息处理器调度查找对应的处理器
     */
    private String handlerKey = MqUtil.handlerKeyByMsgType(this.getClass());
    /**
     * 消息时间
     */
    protected long msgTime = System.currentTimeMillis();

}
