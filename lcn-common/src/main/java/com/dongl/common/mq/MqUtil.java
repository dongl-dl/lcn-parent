package com.dongl.common.mq;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MqUtil.java
 * @Description 通过 handlerKey 获取处理类信息
 * @createTime 2021-07-21 22:50:00
 */
public class MqUtil {
    public static final String HANDLER_KEY_NAME = "handlerKey";

    public static String handlerKeyByMsgType(Class<?> msgType) {
        return msgType.getSimpleName();
    }
}
