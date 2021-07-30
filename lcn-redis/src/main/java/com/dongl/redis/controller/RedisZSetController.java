package com.dongl.redis.controller;

import com.dongl.common.base.ResponseParams;
import com.dongl.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description zset
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/zset")
public class RedisZSetController {


    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/testZSSet")
    public ResponseParams testZSSet() {
        ResponseParams responseParams = new ResponseParams("增加有序集合");
        boolean b = redisUtil.zSSet("testZSSet", "DONGL", 100);
        if (b) {
            Set<Object> testZSSet = redisUtil.zSRange("testZSSet", 0, -1);
            return responseParams.success("20001" , testZSSet);
        }
        return responseParams.error();
    }

    @GetMapping("/testZSRange")
    public ResponseParams testZSRange() {
        ResponseParams responseParams = new ResponseParams("获取zset指定范围内的集合");
        redisUtil.zSSet("testZSSet1", "DONGL1", 100);
        redisUtil.zSSet("testZSSet1", "DONGL2", 50);
        redisUtil.zSSet("testZSSet1", "DONGL3", 20);
        Set<Object> testZSSet = redisUtil.zSRange("testZSSet1", 0, -1);
        return responseParams.success("20001" , testZSSet);
    }

    @GetMapping("/testZSSize")
    public ResponseParams testZSSize() {
        ResponseParams responseParams = new ResponseParams("获取zset集合数量");
        redisUtil.zSSet("testZSSet2", "DONGL1", 100);
        redisUtil.zSSet("testZSSet2", "DONGL2", 50);
        redisUtil.zSSet("testZSSet2", "DONGL3", 20);
        Long size = redisUtil.zSSize("testZSSet2");
        return responseParams.success("20001" , size);
    }

    @GetMapping("/testZSRemove")
    public ResponseParams testZSRemove() {
        ResponseParams responseParams = new ResponseParams("根据key和value移除指定元素");
        redisUtil.zSSet("testZSSet3", "DONGL1", 10);
        redisUtil.zSSet("testZSSet3", "DONGL2", 8);
        redisUtil.zSSet("testZSSet3", "DONGL3", 5);
        redisUtil.zSRemove("testZSSet3" , "DONGL2" , "DONGL3"); //values不能为空
        Long size = redisUtil.zSSize("testZSSet3");
        return responseParams.success("20001" , size);
    }

    @GetMapping("/testZSScore")
    public ResponseParams testZSScore() {
        ResponseParams responseParams = new ResponseParams("获取对应key和value的score");
        redisUtil.zSSet("testZSSet4", "DONGL1", 10);
        Double zSScore = redisUtil.zSScore("testZSSet4", "DONGL1");
        return responseParams.success("20001" , zSScore);
    }

    @GetMapping("/testZSRangeByScore")
    public ResponseParams testZSRangeByScore() {
        ResponseParams responseParams = new ResponseParams("指定范围内元素排序");
        redisUtil.zSSet("testZSSet5", "DONGL1", 12);
        redisUtil.zSSet("testZSSet5", "DONGL2", 55);
        redisUtil.zSSet("testZSSet5", "DONGL3", 6);
        redisUtil.zSSet("testZSSet5", "DONGL4", 112);
        redisUtil.zSSet("testZSSet5", "DONGL5", 1);
        Set<Object> testZSSet = redisUtil.zSRangeByScore("testZSSet5", 5, 110);
        return responseParams.success("20001" , testZSSet);
    }

    @GetMapping("/testZSIncrementScore")
    public ResponseParams testZSIncrementScore() {
        ResponseParams responseParams = new ResponseParams("根据key和value移除指定元素");
        redisUtil.zSSet("testZSSet6", "DONGL1", 50);
        redisUtil.zSIncrementScore("testZSSet6" , "DONGL1", 100);
        redisUtil.zSIncrementScore("testZSSet6" , "DONGL2", 45); //如果value不存在会自动添加
        Set<Object> testZSSet6 = redisUtil.zSRange("testZSSet6", 0, -1);
        return responseParams.success("20001" , testZSSet6);
    }

    @GetMapping("/testZSRank")
    public ResponseParams testZSRank() {
        ResponseParams responseParams = new ResponseParams("获取元素的索引");
        redisUtil.zSSet("testZSSet7", "DONGL1", 12);
        redisUtil.zSSet("testZSSet7", "DONGL2", 55);
        redisUtil.zSSet("testZSSet7", "DONGL3", 6);
        redisUtil.zSSet("testZSSet7", "DONGL4", 112);
        redisUtil.zSSet("testZSSet7", "DONGL5", 1);
        Object result = redisUtil.zSRank("testZSSet7", "DONGL2");
        return responseParams.success("20001" , result);
    }
}
