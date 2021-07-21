package com.dongl.rocketmq.controller;

import com.dongl.common.entity.MqUser;
import com.dongl.common.entity.User;
import com.dongl.common.mq.message.*;
import com.dongl.rocketmq.service.RocketMqService;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description mq 控制层
 * @createTime 2021-06-22 13:45:00
 */
@RestController
@RequestMapping("/mq")
public class RocketMQController {

    @Autowired
    private RocketMqService rocketMqService;

    @GetMapping("/sendMsg01")
    public String sendMsg01(){
        User user = new User();
        user.setId(1);
        user.setName("董亮");
        String result = rocketMqService.sendMsg01(SynUserInfoMsg.builder().id(UUID.randomUUID().toString()).uid("U2022107211059").user(user).build());
        return result;
    }

    @GetMapping("/sendMsg02")
    public String sendMsg02(){
        String result = rocketMqService.sendMsg02(UserLoginSuccessMsg.builder().id(UUID.randomUUID().toString()).uid("U2022107211059").loginTime("2021-07-21").build());
        return result;
    }

//    @GetMapping("/sendMsg03")
//    public String sendMsg03(){
//        String result = rocketMqService.sendOneWayMsg();
//        return result;
//    }
//    @GetMapping("/sendMsg04")
//    public String sendMsg04(){
//        String result = rocketMqService.sendOrderlyMsg();
//        return result;
//    }

    @GetMapping("/sendTransactionMessage")
    public String sendTransactionMessage(){
        MqUser user = MqUser.builder().name("事务消息").age(18).sex("男").address("浙江省 杭州市 上城区 xxx街道 xxx小区 7号楼一单元602").build();
        UserRegisterMsg message = UserRegisterMsg.builder().id(UUID.randomUUID().toString()).registerTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).mqUser(user).build();
        String result = rocketMqService.sendTransactionMessage(message);
        return result;
    }
}
