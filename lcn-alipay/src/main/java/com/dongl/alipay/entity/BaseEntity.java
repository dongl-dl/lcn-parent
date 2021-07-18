package com.dongl.alipay.entity;




import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体公共属性
 * @author Administrator
 *
 */
@Data
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;//唯一id
	

	private Date createTime;//创建时间

	private Date updateTime;//更新时间
}
