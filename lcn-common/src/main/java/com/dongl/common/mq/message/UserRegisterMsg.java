package com.dongl.common.mq.message;

import com.dongl.common.entity.MqUser;
import com.dongl.common.mq.BaseMsg;
import lombok.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserRegisterMsg.java
 * @Description 用户注册信息
 * @createTime 2021-07-21 10:10:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterMsg extends BaseMsg {
    /**
     * 消息ID，可用于控制台消息补发，请保证全局唯一
     */
    private String id;

    /**
     * 账户uid
     */
    private String uid;

    //注册时间
    private String registerTime;

    //用户信息
    private MqUser mqUser;
}
