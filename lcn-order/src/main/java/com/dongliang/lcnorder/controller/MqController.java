package com.dongliang.lcnorder.controller;

import com.dongliang.lcnorder.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MqController.java
 * @Description TODO
 * @createTime 2021-06-17 16:50:00
 */
@RestController
@RequestMapping("mq")
public class MqController {
    @Autowired
    private MqService mqService;

    @GetMapping("sendMsg")
    public String sendMsg(){
        String result = mqService.sendMsg02();
        System.out.println("order--------------:" + result);
        return result;
    }
}
