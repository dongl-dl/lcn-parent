package com.dongl.handlingexception.entity;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ErrorEnum.java
 * @Description TODO
 * @createTime 2021-07-20 16:07:00
 */
public enum  ErrorEnum {
    // 数据操作错误定义
    SUCCESS("200", "成功"),
    NO_PERMISSION("403","你没得权限"),
    NO_AUTH("401","未登录"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器异常"),
            ;

    /** 错误码 */
    private String errorCode;

    /** 错误信息 */
    private String errorMsg;

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
