package com.dongl.common.mq;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MsgHandlerDispatcher.java
 * @Description 消息处理器接口
 *              处理消息时，需要做幂等性处理，以防止同一条消息的重复消费   多个业务同时消费一条消息时，请在不同的业务处理器中处理
 * @createTime 2021-07-21 22:50:00
 */
public interface MsgHandler<M extends BaseMsg> {

    /**
     * 消息处理方法
     * <p>
     * 基于幂等性处理，对于已经被成功处理过的消息，再次消费到时，应作处理成功反馈
     * </p>
     *
     * @param msg 消息体
     * @return true:处理成功<br/>
     * false:处理失败
     */
    boolean process(M msg);
}
