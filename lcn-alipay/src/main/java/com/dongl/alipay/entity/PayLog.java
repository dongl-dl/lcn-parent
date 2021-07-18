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
public class PayLog extends BaseEntity{

	public final static int pay_status_waiting = 0;//待支付
	public final static int pay_status_paid = 1;//已支付
	public final static int pay_status_refunded = 2;//已全额退款
	public final static int pay_status_part_refunded = 20;//已部分退款
	public final static int pay_status_closed = 3;//已关闭
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sn;//支付记录编号
	
	private String paySn;//支付平台交易号
	
	@ValidateEntity(required=false,minValue=0,errorRequiredMsg="请填写支付金额",errorMinValueMsg="支付金额不能小于0")
	private BigDecimal totalAmount;//支付金额
	
	@ValidateEntity(required=true,errorRequiredMsg="请填写订单标题",maxLength=256,errorMaxLengthMsg="订单标题不能超过256个字符")
	private String title;//支付订单标题

	@ValidateEntity(required=false)
	private String info;//支付订单详情
	
	private Integer status = pay_status_waiting;//支付订单状态
	
	private Payment payment ;//支付方式

	private BigDecimal totalRefundAmount = new BigDecimal(0);//退款总金额
	
	private Date payTime;//支付时间
	
	private Date refundTime;//退款时间
	
}
