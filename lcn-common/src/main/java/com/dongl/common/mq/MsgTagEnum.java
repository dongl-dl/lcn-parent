package com.dongl.common.mq;

/**
 * 消息tag 枚举类
 */
public enum MsgTagEnum {
    LOGIN("login", "登录成功"),
    REGISTER("register", "注册成功"),
    SYN_USER_INFO("synUserInfo","同步用户信息");

    private String tag;

    private String description;

    private MsgTagEnum(String tag, String description) {
        this.tag = tag;
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

}
