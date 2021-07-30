package com.dongl.redis.controller;

import com.dongl.common.base.ResponseParams;
import com.dongl.redis.entrity.TestModel;
import com.dongl.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description hash
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/hash")
public class RedisHashController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/testHget")
    public ResponseParams testHget()  {
        ResponseParams responseParams = new ResponseParams("获取item对应的键值");
        redisUtil.hset("testHget", "dongl", "456123");
        Object hget = redisUtil.hget("testHget", "dongl");
        return responseParams.success("20001", hget);
    }

    @GetMapping("/testHmget")
    public ResponseParams testHmget() {
        ResponseParams responseParams = new ResponseParams("获取hashKey对应的所有键值");
        redisUtil.hset("testHmget", "testHmget1", "testHmget1");
        redisUtil.hset("testHmget", "testHmget2", "testHmget2");
        Map<Object, Object> map = redisUtil.hmget("testHmget");
        return responseParams.success("20001", map);
    }

    // 测试放在hash 里面的对象
    @GetMapping("/testHsetObject")
    public ResponseParams testHsetObject() {
        ResponseParams responseParams = new ResponseParams("向一张hash表中放入数据,");
        // 测试对象
        TestModel testModel = new TestModel();
        testModel.setId(System.currentTimeMillis());
        testModel.setName("测试");
        redisUtil.hset("hash", "testModel", testModel);
        TestModel testModel2 = (TestModel) redisUtil.hget("hash", "testModel");
        return responseParams.success("20001", testModel2);
    }

    // 太奇妙了 放进去Long 取出来会根据大小变为相应的数据类型
    @GetMapping("/testHsetStringStringObjectLong")
    public ResponseParams testHsetStringStringObjectLong() {
        ResponseParams responseParams = new ResponseParams("hash表中数据类型转化");
        redisUtil.hset("testHsetStringStringObjectLong", "int", 100L); // java.lang.Integer 读取来是inter
        String typeName = redisUtil.hget("testHsetStringStringObjectLong", "int").getClass().getTypeName();
        redisUtil.hset("testHsetStringStringObjectLong", "long", System.currentTimeMillis()); // java.lang.Integer 读取来是inter
        Object hget1 = redisUtil.hget("testHsetStringStringObjectLong", "long");
        String typeName1 = hget1.getClass().getTypeName();
        return responseParams.success("20001", typeName+typeName1);
    }

    @GetMapping("/testHdel")
    public ResponseParams testHdel() {
        ResponseParams responseParams = new ResponseParams("删除hash表中的值");
        redisUtil.hset("testHdel", "int1", 211);
        redisUtil.hset("testHdel", "int2", 985);
        redisUtil.hdel("testHdel", "int1");
        return responseParams.success("20001", redisUtil.hget("testHdel", "int1") +""+ redisUtil.hget("testHdel", "int1"));
    }

    @GetMapping("/testHHasKey")
    public ResponseParams testHHasKey() {
        ResponseParams responseParams = new ResponseParams("判断hash表中是否有该项的值");
        redisUtil.hset("testHHasKey", "int", 100);
        return responseParams.success("20001", redisUtil.hHasKey("testHHasKey", "int"));
    }

    @GetMapping("/testHincr")
    public ResponseParams testHincr(){
        ResponseParams responseParams = new ResponseParams("hash递增");
        return responseParams.success("20001", redisUtil.hincr("testHincr", "testHincr", 1));
    }

    @GetMapping("/testHdecr")
    public ResponseParams testHdecr() {
        ResponseParams responseParams = new ResponseParams("hash递增");
        System.err.println(redisUtil.hincr("testHincr", "testHincr", 1));
        double hincr = redisUtil.hincr("testHincr", "testHincr", 1);
        return responseParams.success("20001", hincr);
    }

}