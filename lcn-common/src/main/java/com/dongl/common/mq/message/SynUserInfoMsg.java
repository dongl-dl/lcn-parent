package com.dongl.common.mq.message;

import com.dongl.common.entity.User;
import com.dongl.common.mq.BaseMsg;
import lombok.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName SynUserInfoMsg.java
 * @Description 用户信息
 * @createTime 2021-07-21 10:10:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SynUserInfoMsg extends BaseMsg {
    /**
     * 消息ID，可用于控制台消息补发，请保证全局唯一
     */
    private String id;

    /**
     * 账户uid
     */
    private String uid;

    //用户信息
    private User user;
}
