package com.dongl.rocketmq.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongl.common.mq.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName TaskMsgHandlerDispatcher.java
 * @Description 同一个 topic 接受不同类型的消息  通过不同子类进行处理
 * @createTime 2021-07-21 09:50:00
 */
@Slf4j
@Component
public class TaskMsgHandlerDispatcher implements ApplicationContextAware, MsgHandlerDispatcher {
    //存放处理消息的 handlerBeans
    private Map<String, HandlerNMsgType> handlers = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        assembleHandlers(applicationContext);
    }

    /**
     * 项目启动时 初始化能够处理消息的 bean 信息到 handlers
     * @param applicationContext
     */
    private void assembleHandlers(ApplicationContext applicationContext) {
        Map<String, MsgHandler> handlerBeans = applicationContext.getBeansOfType(MsgHandler.class);
        handlerBeans.entrySet().forEach(handlerEntry -> {
            MsgHandler handler = handlerEntry.getValue();
            Class<?> msgType = resolveHandlerMsgType(handler);
            handlers.put(MqUtil.handlerKeyByMsgType(msgType), new HandlerNMsgType(handler, msgType));
            log.info("Message [{}] handler [{}] assembled.", msgType.getSimpleName(), handler.getClass().getSimpleName());
        });
    }

    /**
     * 检验 MsgHandler 是否合法
     * @param handler
     * @return
     */
    private Class<?> resolveHandlerMsgType(MsgHandler handler) {
        Class<?> msgType = null;
        ResolvableType[] interfaces = ResolvableType.forClass(handler.getClass()).getInterfaces();
        for (ResolvableType rt : interfaces) {
            if (rt.resolve().equals(MsgHandler.class)) {
                msgType = rt.getGeneric(0).resolve();
                break;
            }
        }
        return msgType;
    }

    /**---------------------------------------------处理消息--------------------------------------------------*/

    @Override
    public boolean dispatch(String msgContent) {
        if (StringUtils.isBlank(msgContent)) {
            log.info("处理消息----------------msgContent is null!");
            return false;
        }
        JSONObject msgJsonObj = JSON.parseObject(msgContent);
        //获取 handlerKey（消息处理key）
        String handlerKey = msgJsonObj.getString(MqUtil.HANDLER_KEY_NAME);
        //通过 handlerKey 获取handlerBean （消息处理类型实例对象：项目启动时加载到handlers的缓存）
        HandlerNMsgType handlerNMsgType = handlers.get(handlerKey);
        if (Objects.isNull(handlerNMsgType)) {
            log.info("Handler for [{}] does not existed!", handlerKey);
            return false;
        }
        //获取消息内容进行处理
        Object msg = JSON.parseObject(msgContent, handlerNMsgType.getMsgType());
        boolean process = handlerNMsgType.getHandler().process((BaseMsg) msg);
        return process;
    }
}
