package com.dongliang.lcnpay.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_level
 * @author 
 */
@Data
public class UserLevel implements Serializable {
    private Integer id;

    private String levelname;

    private String leveldesc;

    private Date lastupdate;

    private static final long serialVersionUID = 1L;
}