package com.dongl.common.mq;

import lombok.Data;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName HandlerNMsgType.java
 * @Description 消息处理类型
 * @createTime 2021-07-21 10:01:00
 */
@Data
public class HandlerNMsgType {

    private MsgHandler handler;
    private Class<?> msgType;

    public HandlerNMsgType(MsgHandler handler, Class<?> msgType) {
        super();
        this.handler = handler;
        this.msgType = msgType;
    }
}
