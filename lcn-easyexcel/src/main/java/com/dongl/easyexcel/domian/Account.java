package com.dongl.easyexcel.domian;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * eb_account
 */

public class Account implements Serializable {
    private Long id;

    /**
     * 客户唯一ID，关联eb_customer表uid
     */
    private String uid;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * ios 余额
     */
    private BigDecimal iosBalance;

    /**
     * 积分账户
     */
    private Integer pointsBalance;

    private Date createTime;

    private Date updateTime;

    /**
     * 用户状态: 正常-NORMAL  异常-EXCEPTION
     */
    private String status;

    private static final long serialVersionUID = 1L;
}