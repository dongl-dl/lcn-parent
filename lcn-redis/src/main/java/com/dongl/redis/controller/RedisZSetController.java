package com.dongl.redis.controller;

import com.dongl.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description TODO
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/zset")
public class RedisZSetController {


    @Autowired
    private RedisUtil redisUtil;

}
