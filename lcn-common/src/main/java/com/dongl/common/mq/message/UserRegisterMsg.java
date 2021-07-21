package com.dongl.common.mq.message;

import com.dongl.common.entity.MqUser;
import com.dongl.common.mq.BaseMsg;
import com.dongl.common.mq.MsgTagEnum;
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

    //消息Id
    private String id;

    //账户uid
    private String uid;

    private String registerTime;

    private MqUser mqUser;

    @Override
    public String getTag() {
        return MsgTagEnum.REGISTER.getTag();
    }
}
