package com.dongl.redis.controller;

import com.dongl.common.request.ResponseParams;
import com.dongl.redis.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description set
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/set")
public class RedisSetController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/testSGet")
    public ResponseParams testSGet() {
        ResponseParams responseParams = new ResponseParams("根据key获取Set中的所有值");
        redisUtil.sSet("testSGet", "testSGet1");
        redisUtil.sSet("testSGet", "testSGet2");
        Set<Object> testSGet = redisUtil.sGet("testSGet");
        return responseParams.success("20001" , StringUtils.join(testSGet, ","));
    }

    @GetMapping("/testSHasKey")
    public ResponseParams testSHasKey() {
        ResponseParams responseParams = new ResponseParams("根据value从一个set中查询,是否存在");
        redisUtil.sSet("testSHasKey", "value1");
        redisUtil.sSet("testSHasKey", "value2");
        redisUtil.sSet("testSHasKey", "value3");
        return responseParams.success("20001" , redisUtil.sHasKey("testSHasKey", "value2"));

    }

    @GetMapping("/testSSet")
    public ResponseParams testSSet() {
        ResponseParams responseParams = new ResponseParams("将数据批量放入set缓存");
        redisUtil.sSet("testSSet", "a" , "b" , "c" , "d" , "e");
        return responseParams.success("20001" , redisUtil.sGet("testSSet"));
    }

    @GetMapping("/testSSetAndTime")
    public ResponseParams testSSetAndTime() {
        ResponseParams responseParams = new ResponseParams("将set数据放入缓存、设置缓存过期时间");
        redisUtil.sSetAndTime("testSSetAndTime", 20, "testSSetAndTime1");
        redisUtil.sSetAndTime("testSSetAndTime", 5, "testSSetAndTime2");
        return responseParams.success("20001" , StringUtils.join(redisUtil.sGet("testSSetAndTime"), ","));
    }

    @GetMapping("/testSGetSetSize")
    public ResponseParams testSGetSetSize() {
        ResponseParams responseParams = new ResponseParams("获取set缓存的长度");
        redisUtil.sSetAndTime("testSGetSetSize", 20, "testSGetSetSize1");
        redisUtil.sSetAndTime("testSGetSetSize", 5, "testSGetSetSize");
        return responseParams.success("20001" , redisUtil.sGetSetSize("testSGetSetSize"));
    }

    @GetMapping("/testSetRemove")
    public ResponseParams testSetRemove() {
        ResponseParams responseParams = new ResponseParams("set 移除值为value的");
        redisUtil.sSetAndTime("testSetRemove", 20, "testSetRemove1");
        redisUtil.sSetAndTime("testSetRemove", 5, "testSetRemove");
        redisUtil.setRemove("testSetRemove", "testSetRemove");
        return responseParams.success("20001" , redisUtil.sGetSetSize("testSetRemove"));
    }
}
