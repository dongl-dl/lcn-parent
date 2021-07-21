package com.dongl.rocketmq.mq.process;

import com.dongl.common.mq.MsgHandler;
import com.dongl.common.mq.message.UserRegisterMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName SynUserInfoProcess.java
 * @Description 处理用户注册信息
 * @createTime 2021-07-21 10:07:00
 */
@Slf4j
@Service
public class UserRegisterProcess implements MsgHandler<UserRegisterMsg> {
    @Override
    public boolean process(UserRegisterMsg msg) {
        //TODO 处理具体的业务逻辑
        System.out.println("处理用户注册信息" + msg.getMqUser().toString());
        return true;
    }
}
