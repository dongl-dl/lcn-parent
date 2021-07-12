package com.dongliang.lcnorder.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * tbl_order
 * @author 
 */
@Data
@Builder
public class TblOrder implements Serializable {
    private Integer id;

    private String orderName;

    private String uid;

    private String payType;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}