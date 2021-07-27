package com.dongl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * tbl_order
 * @author 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TblOrder implements Serializable {
    private Long id;

    private String orderNo;

    private String orderName;

    private String uid;

    private String payType;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}