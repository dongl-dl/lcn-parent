package com.dl.user.controller;

import com.alibaba.fastjson.JSON;
import com.dl.user.config.threadpool.RocketMQConfig;
import com.dl.user.domain.MqUser;
import com.dl.user.domain.User;
import com.dl.user.service.RocketMqService;
import com.dl.user.service.UserService;
import com.dl.user.util.ResponseParams;
import com.dongl.common.mq.message.SynUserInfoMsg;
import com.dongl.common.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2021-06-22 13:45:00
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RocketMqService rocketMqService;

    @Autowired
    private UserService userService;

    @Autowired
    private RocketMQConfig config;


    /***
     * 用户登录
     */
    @RequestMapping(value = "/login")
    public ResponseParams login(String username, String password , HttpServletResponse response){
        ResponseParams responseParams = new ResponseParams("用户登录");
        //查询用户信息
        User user = userService.findById(username);

        if(user!=null && checkpw(password,user.getPassword())){
            //设置令牌信息
            Map<String,Object> info = new HashMap<String,Object>();
            info.put("role","USER");
            info.put("success","SUCCESS");
            info.put("username",username);
            //生成令牌 过期时间单位为秒
            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(info),10L);

            //添加到cookie
            Cookie cookie = new Cookie("Authorization" , jwt);
            response.addCookie(cookie);
            return responseParams.success("20001", "登录成功" ,jwt);
        }
        return responseParams.error("30002" , "登录名或密码错误");
    }

    private boolean checkpw(String password, String password1) {
        if(StringUtils.equals(password1 , password)){
            return true;
        }
        return false;
    }


    @GetMapping("/sendMsg")
    public String sendMsg(){
        String result = rocketMqService.sendMsg();
        return result;
    }

    /**
     * 发送同步消息
     * @return
     */
    @GetMapping("/sendMsg01")
    public String sendMsg01(){
        com.dongl.common.entity.User user = com.dongl.common.entity.User.builder().id(1).name("董亮").build();
        SynUserInfoMsg synUserInfoMsg = SynUserInfoMsg.builder().id(UUID.randomUUID().toString()).uid("U2022107211059").user(user).build();
        ImmutableTriple<String , String, String> mqConfig = ImmutableTriple.of(config.getGroupName() , config.getTopic() , config.getTag());
        String result = null;
        try {
            result = rocketMqService.sendMsg01(synUserInfoMsg ,mqConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
