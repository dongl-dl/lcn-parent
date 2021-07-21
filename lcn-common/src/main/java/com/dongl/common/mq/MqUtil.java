package com.dongl.common.mq;

public class MqUtil {
    public static final String HANDLER_KEY_NAME = "handlerKey";

    public static String handlerKeyByMsgType(Class<?> msgType) {
        return msgType.getSimpleName();
    }
}
