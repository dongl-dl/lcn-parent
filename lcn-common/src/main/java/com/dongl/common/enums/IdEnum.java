package com.dongl.common.enums;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName IdEnum.java
 * @Description  ID/Code生成前缀及随机位数配置枚举
 * @createTime 2021-07-26 17:18:00
 */
public enum  IdEnum {
    UID() {
        @Override
        public String getPrefix() {
            return "U";
        }

        @Override
        public int getRandomNum() {
            return 4;
        }

        @Override
        public String getDescription() {
            return "用户UID";
        }
    },
    OID() {
        @Override
        public String getPrefix() {
            return "U";
        }

        @Override
        public int getRandomNum() {
            return 4;
        }

        @Override
        public String getDescription() {
            return "订单ID";
        }
    };

    /**
     * 前缀
     *
     * @return
     */
    public abstract String getPrefix();

    /**
     * 后缀随机位数
     *
     * @return
     */
    public abstract int getRandomNum();

    /**
     * 描述
     *
     * @return
     */
    public abstract String getDescription();
}
