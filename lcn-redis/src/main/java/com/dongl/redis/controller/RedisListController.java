package com.dongl.redis.controller;

import com.dongl.common.base.ResponseParams;
import com.dongl.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description list
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/list")
public class RedisListController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/testLGet")
    public ResponseParams testLGet() {
        ResponseParams responseParams = new ResponseParams("获取list缓存的内容");
        Map<String , List<Object>> map = new HashMap<>();
        redisUtil.lSet("testLGet", "testLGet0", 10); // 10秒过期
        redisUtil.lSet("testLGet", "testLGet1", 10);
        // 查询三个元素 2-0+1
        List<Object> list1 = redisUtil.lGet("testLGet", 0, 2);
        // 查询两个
        List<Object> list2 = redisUtil.lGet("testLGet", 0, 1);
        // 查询全部
        List<Object> list3 = redisUtil.lGet("testLGet", 0, -1);
        map.put("list1" , list1);
        map.put("list2" , list2);
        map.put("list3" , list3);
        return responseParams.success("20001" , map);

    }

    @GetMapping("/testLGetListSize")
    public ResponseParams testLGetListSize() {
        ResponseParams responseParams = new ResponseParams("获取list缓存的长度");
        // 看看重复元素会怎么处理
        redisUtil.lSet("testLGetListSize", "testLGetListSize0", 10); // 10秒过期
        redisUtil.lSet("testLGetListSize", "testLGetListSize0", 10);
        return responseParams.success("20001" , redisUtil.lGetListSize("testLGetListSize"));
    }


    @GetMapping("/testLGetIndex")
    public ResponseParams testLGetIndex() {
        ResponseParams responseParams = new ResponseParams("通过索引 获取list中的值");
        redisUtil.lSet("testLGetIndex", "testLGetIndex0", 50); // 10秒过期
        redisUtil.lSet("testLGetIndex", "testLGetIndex1", 50);
        return responseParams.success("20001" , redisUtil.lGetIndex("testLGetIndex", 0));
    }

    @GetMapping("/testLSetStringObject")
    public ResponseParams testLSetStringObject() {
        ResponseParams responseParams = new ResponseParams("将list放入缓存");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject0");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject1");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject2");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject1");
        return responseParams.success("20001" ,  redisUtil.lGet("testLGet", 0, -1));
    }

    @GetMapping("/testLSetStringObjectLong")
    public ResponseParams testLSetStringObjectLong() {
        ResponseParams responseParams = new ResponseParams("将list<Long>放入缓存");
        redisUtil.lSet("testLSetStringObjectLong", 100L);
        redisUtil.lSet("testLSetStringObjectLong", 200L);
        redisUtil.lSet("testLSetStringObjectLong", 300L);
        return responseParams.success("20001" , redisUtil.lGet("testLGet", 0, -1));
    }

    @GetMapping("/testExpitestLUpdateIndexre")
    public ResponseParams testLUpdateIndex() {
        ResponseParams responseParams = new ResponseParams("根据索引修改list中的某条数据");
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex0");
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex1");
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex2");
        Object obj = redisUtil.lUpdateIndex("testLUpdateIndex", 0, "更新的");
        return responseParams.success("20001" , obj);
    }

    @GetMapping("/testLRemove")
    public ResponseParams testLRemove() {
        ResponseParams responseParams = new ResponseParams("移除N个值为value");
        redisUtil.lSet("testLRemove2", "testLRemove0");
        redisUtil.lSet("testLRemove2", "testLRemove1");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        redisUtil.lSet("testLRemove2", "testLRemove3");
        redisUtil.lSet("testLRemove2", "testLRemove4");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        Object obj = redisUtil.lRemove("testLRemove2", 2, "testLRemove2");
        return responseParams.success("20001" , obj);
    }

}
