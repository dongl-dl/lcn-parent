package com.dongliang.lcnorder.config.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName PostConstructExampleBean.java
 * @Description
 * @createTime 2021-05-18 18:00:00
 */
@Component
@Slf4j
public class PostConstructExampleBean {

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        log.info(Arrays.asList(environment.getDefaultProfiles()).toString());
        System.out.println("----------------------------PostConstruct");
    }

}
