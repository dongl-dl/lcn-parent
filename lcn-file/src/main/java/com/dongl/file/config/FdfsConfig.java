package com.dongl.file.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName FdfsConfig.java
 * @Description 导入FastDFS-Client组件
 * @createTime 2021-07-07 10:44:00
 */
@Configuration
@Import(FdfsClientConfig.class)  //只需要一行注解 @Import(FdfsClientConfig.class)就可以拥有带有连接池的FastDFS Java客户端了
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)  // 解决jmx重复注册bean的问题
public class FdfsConfig {
}
