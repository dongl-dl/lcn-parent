package com.dongl.alipay.entity;
/**
 * 支付方式枚举类
 * @author Administrator
 *
 */
public enum Payment {

	Alipay(true,"支付宝"),
	WxPay(false,"微信支付");
	
	private boolean enable;
	private String name;
	
	private Payment(boolean enable,String name){
		this.enable = enable;
		this.name = name;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
