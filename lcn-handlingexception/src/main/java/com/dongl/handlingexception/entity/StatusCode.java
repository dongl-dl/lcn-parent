package com.dongl.handlingexception.entity;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName StatusCode.java
 * @Description TODO
 * @createTime 2021-07-20 18:09:00
 */
public class StatusCode {
    public static final String OK = "20000";//成功
    public static final String ERROR = "20001";//失败
    public static final String LOGINERROR = "20002";//用户名或密码错误
    public static final String ACCESSERROR = "20003";//权限不足
    public static final String REMOTEERROR = "20004";//远程调用失败
    public static final String REPERROR = "20005";//重复操作
    public static final String NOTFOUNDERROR = "20006";//没有对应的抢购数据
}
