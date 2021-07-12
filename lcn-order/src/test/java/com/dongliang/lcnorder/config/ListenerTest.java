package com.dongliang.lcnorder.config;

import com.dongliang.lcnorder.config.initialize.NotifyEvent;
import com.dongliang.lcnorder.util.ThreadPoolManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ListenerTest.java
 * @Description
 * @createTime 2021-05-18 17:51:00
 */
@SpringBootTest
public class ListenerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testListener() {
        NotifyEvent event = new NotifyEvent("object", "abc@qq.com", "This is the content");
        webApplicationContext.publishEvent(event);
    }


    public static void main(String[] args) {
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("ThreadPoolManager------------------------");
            }
        });
    }
}
