package com.dongl.common.entity.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * The class Gaode base dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class GaodeBaseDto implements Serializable{

	private static final long serialVersionUID = 5894304327211503218L;
	/**
	 * 状态
	 */
	private String status;

	/**
	 * 响应信息
	 */
	private String info;

	/**
	 * 响应编码
	 */
	private String infocode;
}
