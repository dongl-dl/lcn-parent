package com.dongl.common.constant;


public class RedisConstant {
    /**
     * 签名秘钥信息
     */
    public static final String REDIS_SIGN_SECRET = "SIGN:SECRET:";
    /**
     * 签名秘钥信息
     */
    public static final String REDIS_APP_INFO = "APP:INFO:";
    /**
     * 签名nonce随机字符串信息
     */
    public static final String REDIS_SIGN_NONCE = "SIGN:NONCE:";
    public static final String REDIS_GOODS_NONCE = "GOODS:";
    public static final String REDIS_ORDER_RENT_BOOK = "ORDER:BOOK:RENT:";
    public static final String REDIS_ORDER_FLOW_BOOK = "ORDER:BOOK:FLOW:";
    public static final String REDIS_ORDER_FLOW_COMMIT = "ORDER:BOOK:COMMIT:";

    /**
     * 未结算金额
     */
    public static final String REDIS_UNSETTLED_AMOUNT = "ACCOUNT:UNSETTLED:AMOUNT:";
    /**
     * 账户余额
     */
    public static final String REDIS_ACCOUNT_BALANCE = "ACCOUNT:BALANCE:";

    /**
     * 内容商城购物车
     */
    public static final String REDIS_SHOPPING_CART = "SHOPPING:CART:";
}
