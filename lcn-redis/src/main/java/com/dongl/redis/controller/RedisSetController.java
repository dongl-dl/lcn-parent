package com.dongl.redis.controller;

import com.dongl.common.request.ResponseParams;
import com.dongl.redis.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import java.util.concurrent.TimeUnit;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description TODO
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/set")
public class RedisSetController {


    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置缓存过期时间
     */
    @GetMapping("/testExpire")
    public ResponseParams testExpire() throws Exception {
        ResponseParams responseParams = new ResponseParams("设置缓存过期时间");
        redisUtil.set("aaaKey", "aaaValue");
        redisUtil.expire("aaaKey", 10);
        Object aaaValue = redisUtil.get("aaaKey");
        return responseParams.success("20001" , aaaValue);
    }



    /*********************************Set***********************************/
    @GetMapping("/testExpire")
    public void testSGet() throws Exception {
        redisUtil.sSet("testSGet", "testSGet1");
        redisUtil.sSet("testSGet", "testSGet2");
        Set<Object> testSGet = redisUtil.sGet("testSGet");
        System.err.println(StringUtils.join(testSGet, ","));
    }

    @GetMapping("/testExpire")
    public void testSHasKey() throws Exception {
        redisUtil.sSet("testSHasKey", "value1");
        redisUtil.sSet("testSHasKey", "value2");
        redisUtil.sSet("testSHasKey", "value3");
        System.err.println(redisUtil.sHasKey("testSHasKey", "value2"));

    }

    @GetMapping("/testExpire")
    public void testSSet() throws Exception {
        redisUtil.sSet("testSSet", "a" , "b" , "c" , "d" , "e");
        System.out.println(redisUtil.sGet("testSSet"));
    }

    @GetMapping("/testExpire")
    public void testSSetAndTime() throws Exception {
        redisUtil.sSetAndTime("testSSetAndTime", 20, "testSSetAndTime1");
        System.err.println(StringUtils.join(redisUtil.sGet("testSSetAndTime"), ","));
        redisUtil.sSetAndTime("testSSetAndTime", 5, "testSSetAndTime2");
        System.err.println(StringUtils.join(redisUtil.sGet("testSSetAndTime"), ","));
        TimeUnit.SECONDS.sleep(5);
        System.err.println(StringUtils.join(redisUtil.sGet("testSSetAndTime"), ",-----"));
    }

    @GetMapping("/testExpire")
    public void testSGetSetSize() throws Exception {
        redisUtil.sSetAndTime("testSGetSetSize", 20, "testSGetSetSize1");
        redisUtil.sSetAndTime("testSGetSetSize", 5, "testSGetSetSize");
        System.err.println(redisUtil.sGetSetSize("testSGetSetSize"));
    }

    @GetMapping("/testExpire")
    public void testSetRemove() throws Exception {
        redisUtil.sSetAndTime("testSetRemove", 20, "testSetRemove1");
        redisUtil.sSetAndTime("testSetRemove", 5, "testSetRemove");
        System.out.println(redisUtil.sGetSetSize("testSetRemove"));
        redisUtil.setRemove("testSetRemove", "testSetRemove");
        System.out.println(redisUtil.sGetSetSize("testSetRemove"));

    }
}
