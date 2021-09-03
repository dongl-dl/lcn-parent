package com.dongl.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* aliyun oss 的yml配置参数
*
* @author D-L
* @date 2021/9/02 21:18
*/
@Data
@Component
@ConfigurationProperties(prefix="oss.aliyun-oss")
public class AliyunOssConfig {

	private String endpoint;
	private String publicBucketName;
	private String privateBucketName;
	private String accessKeyId;
	private String accessKeySecret;
}



