package com.dongliang.lcnorder.config.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName LogicInConstructorExampleBean.java
 * @Description TODO
 * @createTime 2021-05-19 09:32:00
 */
@Slf4j
@Component
public class LogicInConstructorExampleBean {

    private final Environment environment;

    @Autowired
    public LogicInConstructorExampleBean(Environment environment) {
        this.environment = environment;
        log.info(Arrays.asList(environment.getDefaultProfiles()).toString());
        System.out.println("初始化------------------------Constructor");
    }

}
