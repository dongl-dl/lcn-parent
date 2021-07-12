package com.dongliang.lcnorder.config.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName StartupApplicationListenerExample.java
 * @Description TODO
 * @createTime 2021-05-18 17:30:00
 */

@Component
@Slf4j
public class StartupApplicationListenerExample implements ApplicationListener<ContextRefreshedEvent> {

    public static int counter;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // root application context 没有parent
            log.info("Increment counter---------------");
            counter++;
        }
    }

}
