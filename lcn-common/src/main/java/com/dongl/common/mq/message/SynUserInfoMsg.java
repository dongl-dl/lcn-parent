package com.dongl.common.mq.message;

import com.dongl.common.entity.User;
import com.dongl.common.mq.BaseMsg;
import com.dongl.common.mq.MsgTagEnum;
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

    //消息Id
    private String id;

    //账户uid
    private String uid;

    private User user;

    @Override
    public String getTag() {
        return MsgTagEnum.SYN_USER_INFO.getTag();
    }
}
