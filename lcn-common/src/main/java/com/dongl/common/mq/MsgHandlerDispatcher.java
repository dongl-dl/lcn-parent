package com.dongl.common.mq;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MsgHandlerDispatcher.java
 * @Description 消息处理器调度器接口
 * @createTime 2021-07-21 22:50:00
 */
public interface MsgHandlerDispatcher {

    /**
     * 实现处理器调度，并对消息进行处理
     *
     * @param msgContent 消息体JSON串
     * @return true：处理成功</br>
     * false：处理失败
     */
    boolean dispatch(String msgContent);
}
