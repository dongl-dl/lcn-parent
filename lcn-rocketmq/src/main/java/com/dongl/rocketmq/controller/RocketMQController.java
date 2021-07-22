package com.dongl.rocketmq.controller;

import com.dongl.common.entity.MqUser;
import com.dongl.common.entity.User;
import com.dongl.common.mq.message.*;
import com.dongl.rocketmq.config.RocketMQConfig;
import com.dongl.rocketmq.service.RocketMqService;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
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

    @Autowired
    private RocketMQConfig config;

    /**
     * 发送同步消息
     * @return
     */
    @GetMapping("/sendMsg01")
    public String sendMsg01(){
        User user = User.builder().id(1).name("董亮").build();
        SynUserInfoMsg synUserInfoMsg = SynUserInfoMsg.builder().id(UUID.randomUUID().toString()).uid("U2022107211059").user(user).build();
        ImmutableTriple<String , String, String> mqConfig = ImmutableTriple.of(config.getGroupName() , config.getTopic() , config.getTag());
        String result = rocketMqService.sendMsg01(synUserInfoMsg ,mqConfig);
        return result;
    }

    /**
     * 发送异步消息
     * @return
     */
    @GetMapping("/sendMsg02")
    public String sendMsg02(){
        UserLoginSuccessMsg userLoginSuccessMsg = UserLoginSuccessMsg.builder().id(UUID.randomUUID().toString()).uid("U2022107211059").loginTime("2021-07-21").build();
        ImmutableTriple<String , String, String> mqConfig = ImmutableTriple.of(config.getGroupName() , config.getTopic() , config.getTag());
        String result = rocketMqService.sendMsg02(userLoginSuccessMsg ,mqConfig);
        return result;
    }

    /**
     * 发送事务消息
     * @return
     */
    @GetMapping("/sendTransactionMessage")
    public String sendTransactionMessage(){
        MqUser user = MqUser.builder().name("董亮").age(18).sex("男").address("浙江省 杭州市 上城区 xxx街道 xxx小区 7号楼一单元602").build();
        UserRegisterMsg message = UserRegisterMsg.builder().id(UUID.randomUUID().toString()).registerTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).mqUser(user).build();
        ImmutableTriple<String , String, String> mqConfig = ImmutableTriple.of(config.getGroupName() , config.getTopic() , config.getTag());
        String result = rocketMqService.sendTransactionMessage(message ,mqConfig);
        return result;
    }
}
