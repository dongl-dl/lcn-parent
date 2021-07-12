package com.dongliang.lcnorder.util.pagehelper;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName PageUtils.java
 * @Description 分页结果对象
 * @createTime 2021-06-15 15:55:00
 */

@Data
public class PageResult<T extends List> implements Serializable{
	
	private static final long serialVersionUID = -7173074737108151950L;

	//总记录数
	private long total=0;
	
	//结果集
	private List list;

	//当前页码
	private int pageNum=1;
	
	//每页显示条数
	private int pageSize=10;

	//总页数
	private int totalPage=1;
}
