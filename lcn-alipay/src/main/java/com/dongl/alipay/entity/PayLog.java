package com.dongl.alipay.entity;


import com.dongl.alipay.annotion.ValidateEntity;
import lombok.Data;
import lombok.ToString;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付记录实体类
 * @author Administrator
 *
 */
@Data
@ToString
public class PayLog{

	public final static int pay_status_waiting = 0;//待支付
	public final static int pay_status_paid = 1;//已支付
	public final static int pay_status_refunded = 2;//已全额退款
	public final static int pay_status_part_refunded = 20;//已部分退款
	public final static int pay_status_closed = 3;//已关闭

	private Long id;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 支付订单详情
	 */
	private String info;

	/**
	 * 支付平台交易号
	 */
	private String paySn;

	/**
	 * 支付方式
	 */
	private Integer payment;

	/**
	 * 退款总金额
	 */
	private BigDecimal refundAmount;

	/**
	 * 退款详情
	 */
	private String refundInfo;

	/**
	 * 退款记录编号
	 */
	private String refundSn;

	/**
	 * 支付记录编号
	 */
	private String sn;

	/**
	 * 支付订单状态
	 */
	private Integer status;

	/**
	 * 支付订单标题
	 */
	private String title;

	/**
	 * 支付金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 支付时间
	 */
	private Date payTime;

	/**
	 * 退款时间
	 */
	private Date refundTime;

	private static final long serialVersionUID = 1L;
	
}
