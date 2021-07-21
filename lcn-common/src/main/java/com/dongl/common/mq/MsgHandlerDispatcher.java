package com.dongl.common.mq;

/**
 * 消息处理器调度器接口
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
