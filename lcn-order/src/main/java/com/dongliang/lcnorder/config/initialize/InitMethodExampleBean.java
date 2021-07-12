package com.dongliang.lcnorder.config.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName InitMethodExampleBean.java
 * @Description
 * @createTime 2021-05-19 09:25:00
 */
@Slf4j
public class InitMethodExampleBean {

    @Autowired
    private Environment environment;

    public void init() {
        log.info(Arrays.asList(environment.getDefaultProfiles()).toString());
        System.out.println("初始化----------------------Bean");
    }
}
