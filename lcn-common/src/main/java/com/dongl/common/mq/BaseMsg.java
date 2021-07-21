package com.dongl.common.mq;


/**
 * MQ消息体基类
 */
public abstract class BaseMsg {

    /**
     * 消息处理器标记，用于消息处理器调度查找对应的处理器
     */
    private String handlerKey = MqUtil.handlerKeyByMsgType(this.getClass());
    /**
     * 消息时间
     */
    protected long msgTime = System.currentTimeMillis();

    public String getHandlerKey() {
        return handlerKey;
    }

    public void setHandlerKey(String handlerKey) {
        this.handlerKey = handlerKey;
    }

    /**
     * 消息路由/投递标签，对应RabbitMq的RoutingKey，RocketMQ的Tag
     *
     * @return
     */
    public abstract String getTag();

    /**
     * 消息ID，可用于控制台消息补发，请保证全局唯一
     *
     * @return
     */
    public abstract String getId();

    /**
     * 获取用户UID
     *
     * @return
     */
    public abstract String getUid();

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

}
