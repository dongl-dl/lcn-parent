package com.dongl.rocketmq.mq.process;

import com.dongl.common.mq.MsgHandler;
import com.dongl.common.mq.message.UserLoginSuccessMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName SynUserInfoProcess.java
 * @Description 用户登录信息处理
 * @createTime 2021-07-21 10:07:00
 */
@Slf4j
@Service
public class UserLoginSuccessProcess implements MsgHandler<UserLoginSuccessMsg> {
    @Override
    public boolean process(UserLoginSuccessMsg msg) {
        //TODO 处理具体的业务逻辑
        System.out.println("处理登录成功信息" + msg.getLoginTime());
        return true;
    }
}
