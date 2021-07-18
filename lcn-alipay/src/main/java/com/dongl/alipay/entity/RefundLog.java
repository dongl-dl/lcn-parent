package com.dongl.alipay.entity;

import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;

/**
 * 退款记录实体类
 * @author Administrator
 *
 */
@Data
@ToString
public class RefundLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private PayLog payLog;//所属支付记录
	
	private String refundSn;//退款编号
	
	private BigDecimal refundAmount = new BigDecimal(0);//退款金额
	
	private String refundInfo;//退款原因
	
}
