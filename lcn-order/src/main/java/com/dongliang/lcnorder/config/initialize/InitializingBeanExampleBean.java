package com.dongliang.lcnorder.config.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName InitializingBeanExampleBean.java
 * @Description TODO
 * @createTime 2021-05-19 09:17:00
 */
@Component
@Slf4j
public class InitializingBeanExampleBean implements InitializingBean {
    @Autowired
    Environment env;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(Arrays.asList(env.getDefaultProfiles()).toString());
        System.out.println("初始化------InitializingBean");
    }
}
