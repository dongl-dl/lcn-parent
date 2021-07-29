package com.dongl.redis.controller;

import com.dongl.common.request.ResponseParams;
import com.dongl.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description TODO
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/list")
public class RedisListController {


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



    /*********************************List***********************************/
    @GetMapping("/testExpire")
    public void testLGet() throws Exception {
        redisUtil.lSet("testLGet", "testLGet0", 10); // 10秒过期
        redisUtil.lSet("testLGet", "testLGet1", 10);
        // 查询三个元素 2-0+1
        List<Object> list = redisUtil.lGet("testLGet", 0, 2);
        System.err.println("list===" + list);
        // 查询两个
        List<Object> list2 = redisUtil.lGet("testLGet", 0, 1);
        System.err.println("list2===" + list2);
        // 查询全部
        List<Object> list3 = redisUtil.lGet("testLGet", 0, -1);
        System.err.println("list3===" + list3);

    }

    @GetMapping("/testExpire")
    public void testLGetListSize() throws Exception {
        // 看看重复元素会怎么处理
        redisUtil.lSet("testLGetListSize", "testLGetListSize0", 10); // 10秒过期
        System.err.println(redisUtil.lGetListSize("testLGetListSize"));

        redisUtil.lSet("testLGetListSize", "testLGetListSize0", 10);
        System.err.println(redisUtil.lGetListSize("testLGetListSize"));
    }


    @GetMapping("/testExpire")
    public void testLGetIndex() throws Exception {
        redisUtil.lSet("testLGetIndex", "testLGetIndex0", 50); // 10秒过期
        redisUtil.lSet("testLGetIndex", "testLGetIndex1", 50);
        System.err.println(redisUtil.lGetIndex("testLGetIndex", 0));
    }

    @GetMapping("/testExpire")
    public void testLSetStringObject() throws Exception {
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject0");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject1");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject2");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject1");
    }

    @GetMapping("/testExpire")
    public void testLSetStringObjectLong() throws Exception {
        redisUtil.lSet("testLSetStringObjectLong", 100L);
        redisUtil.lSet("testLSetStringObjectLong", 200L);
        redisUtil.lSet("testLSetStringObjectLong", 300L);
    }

    @GetMapping("/testExpire")
    public void testLUpdateIndex() throws Exception {
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex0");
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex1");
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex2");
        Object obj = redisUtil.lUpdateIndex("testLUpdateIndex", 0, "更新的");
        System.out.println(obj);
    }

    @GetMapping("/testExpire")
    public void testLRemove() throws Exception {
        redisUtil.lSet("testLRemove2", "testLRemove0");
        redisUtil.lSet("testLRemove2", "testLRemove1");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        redisUtil.lSet("testLRemove2", "testLRemove3");
        redisUtil.lSet("testLRemove2", "testLRemove4");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        Object obj = redisUtil.lRemove("testLRemove2", 2, "testLRemove2");
        System.out.println(obj);
    }

}
