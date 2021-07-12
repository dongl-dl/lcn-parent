package com.dongliang.lcnorder.config.initialize;

import org.springframework.context.ApplicationEvent;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName NotifyEvent.java
 * @Description
 * @createTime 2021-05-18 17:47:00
 */

public class NotifyEvent extends ApplicationEvent {

    private String email;
    private String content;

    public NotifyEvent(Object source) {
        super(source);
    }

    public NotifyEvent(Object source, String email, String content) {
        super(source);
        this.email = email;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
