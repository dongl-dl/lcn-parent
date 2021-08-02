package com.dongl.redis.controller;

import com.dongl.common.base.ResponseParams;
import com.dongl.redis.entrity.TestModel;
import com.dongl.redis.utils.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description String
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/string")
public class RedisStringController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/testGet")
    public ResponseParams testGet(){
        ResponseParams responseParams = new ResponseParams("普通缓存获取");
        redisUtil.set("aaaKey", "aaaValue");
        Object aaaKey = redisUtil.get("aaaKey");
        return responseParams.success("20001", aaaKey);
    }

    @GetMapping("/testSetStringObject")
    public ResponseParams testSetStringObject(){
        ResponseParams responseParams = new ResponseParams("普通缓存放入");
        boolean aaaKey = redisUtil.set("aaaKey", "aaaValue");
        return responseParams.success("20001", aaaKey);
    }

    @GetMapping("/testSetStringObjectLong")
    public ResponseParams testSetStringObjectLong() {
        ResponseParams responseParams = new ResponseParams("普通缓存放入Long");
        boolean aaaKeyLong = redisUtil.set("aaaKeyLong", 100L);
        return responseParams.success("20001", aaaKeyLong);
    }

    @GetMapping("/testSetObject")
    public ResponseParams testSetObject() {
        ResponseParams responseParams = new ResponseParams("普通缓存放入实体对象");
        // 测试对象
        TestModel testModel = new TestModel();
        testModel.setId(System.currentTimeMillis());
        testModel.setName("测试");
        redisUtil.set("testModel", testModel);
        TestModel testModel2 = (TestModel) redisUtil.get("testModel");
        return responseParams.success("20001", testModel2);
    }

    @GetMapping("/testIncr")
    public ResponseParams testIncr() {
        ResponseParams responseParams = new ResponseParams("递增");
        String key = "testIncr";
        redisUtil.incr(key, 1);
        redisUtil.expire(key, 10); // 缓存失效10s
        return responseParams.success("20001", redisUtil.get(key));
    }

    @GetMapping("/testDecr")
    public ResponseParams testDecr() {
        ResponseParams responseParams = new ResponseParams("递减");
        String key = "Decr_17353620612";
        redisUtil.decr(key, 1);
        redisUtil.expire(key, 10); // 缓存失效10s
        return responseParams.success("20001", redisUtil.get(key));
    }


    @GetMapping("/getAndSet")
    public ResponseParams getAndSet() {
        ResponseParams responseParams = new ResponseParams("");
        Object andSet = redisUtil.getAndSet("dongl", "dongliang");
        return responseParams.success("20001", andSet);
    }

    @GetMapping("/append")
    public ResponseParams append() {
        ResponseParams responseParams = new ResponseParams("");
        redisUtil.append("dongl1", "dl");
        return responseParams.success("20001", null);
    }

}