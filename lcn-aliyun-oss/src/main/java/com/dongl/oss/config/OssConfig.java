package com.dongl.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 的yml配置参数
 *
 * @author D-L
 * @date 2021/9/02 21:18
 */
@Data
@Component
@ConfigurationProperties(prefix="oss")
public class OssConfig {

	/** 存储根路径 **/
	private String fileRootPath;

	/** 公共读取块 **/
	private String filePublicPath;

	/** 私有读取块 **/
	private String filePrivatePath;

	/** oss类型 **/
	private String serviceType;

}



