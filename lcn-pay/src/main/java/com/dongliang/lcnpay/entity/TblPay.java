package com.dongliang.lcnpay.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * tbl_pay
 * @author 
 */
@Data
public class TblPay implements Serializable {
    private Integer id;

    private String payName;

    private static final long serialVersionUID = 1L;
}