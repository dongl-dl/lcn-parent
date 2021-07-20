package com.dongl.rocketmq.controller;

import com.alibaba.fastjson.JSON;

import com.dongl.rocketmq.entity.MqUser;
import com.dongl.rocketmq.service.RocketMqService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2021-06-22 13:45:00
 */
@RestController
@RequestMapping("/mq")
public class RocketMQController {

    @Autowired
    private RocketMqService rocketMqService;


    @GetMapping("/sendMsg")
    public String sendMsg(){
        String result = rocketMqService.sendMsg();
        return result;
    }

    @GetMapping("/sendMsg01")
    public String sendMsg01(){
        String result = rocketMqService.sendMsg01();
        return result;
    }

    @GetMapping("/sendMsg02")
    public String sendMsg02(){
        String result = rocketMqService.sendMsg02();
        return result;
    }

    @GetMapping("/sendMsg03")
    public String sendMsg03(){
        String result = rocketMqService.sendOneWayMsg();
        return result;
    }
    @GetMapping("/sendMsg04")
    public String sendMsg04(){
        String result = rocketMqService.sendOrderlyMsg();
        return result;
    }

    @GetMapping("/sendTransactionMessage")
    public String sendTransactionMessage(){
        MqUser user = new MqUser();
        user.setName("事务消息");
        user.setAge(18);
        user.setSex("男");
        user.setAddress("浙江省 杭州市 上城区 xxx街道 xxx小区 7号楼一单元602");
        String msgStr = JSON.toJSONString(user);
        String result = rocketMqService.sendTransactionMessage(msgStr);
        return result;
    }
}
