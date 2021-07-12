package com.dongliang.lcnorder.config.initialize;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName NotifyListener.java
 * @Description
 * @createTime 2021-05-18 17:49:00
 */
@Component
public class NotifyListener implements ApplicationListener<NotifyEvent> {


    @Override
    public void onApplicationEvent(NotifyEvent event) {
        System.out.println("邮件地址：" + event.getEmail());
        System.out.println("邮件内容：" + event.getContent());
    }
}
