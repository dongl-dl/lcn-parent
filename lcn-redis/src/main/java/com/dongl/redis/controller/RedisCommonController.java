package com.dongl.redis.controller;

import com.dongl.common.base.ResponseParams;
import com.dongl.redis.utils.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisController.java
 * @Description common
 * @createTime 2021-07-29 16:44:00
 */
@RestController
@RequestMapping("/common")
public class RedisCommonController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置缓存过期时间
     */
    @GetMapping("/testExpire")
    public ResponseParams testExpire() {
        ResponseParams responseParams = new ResponseParams("设置缓存过期时间");
        redisUtil.set("aaaKey", "aaaValue");
        redisUtil.expire("aaaKey", 10);
        Object aaaValue = redisUtil.get("aaaKey");
        return responseParams.success("20001" , aaaValue);
    }

    @GetMapping("/testGetExpire")
    public ResponseParams testGetExpire()  {
        ResponseParams responseParams = new ResponseParams("获取指定缓存失效时间");
        redisUtil.set("aaaKey", "aaaValue");
        redisUtil.expire("aaaKey", 100);
        // 设置了缓存就会及时的生效，所以缓存时间小于最初设置的时间
        long aaaKey = redisUtil.getExpire("aaaKey");
        return responseParams.success("20001" , aaaKey);
    }

    @GetMapping("/testHasKey")
    public ResponseParams testHasKey() {
        ResponseParams responseParams = new ResponseParams("判断key是否存在");
        redisUtil.set("aaaKey", "aaaValue");
        // 存在的
        boolean aaaKey = redisUtil.hasKey("aaaKey");
        // 不存在的
        boolean bbbKey = redisUtil.hasKey("bbbKey");
        return responseParams.success("20001" , aaaKey + "---" + bbbKey);
    }

    @GetMapping("/testDel")
    public ResponseParams testDel() {
        ResponseParams responseParams = new ResponseParams("批量删除缓存");
        redisUtil.set("aaaKey", "aaaValue");
        redisUtil.set("bbbKey", "bbbValue");
        redisUtil.set("cccKey", "cccValue");

        // 批量删除缓存
        redisUtil.del("aaaKey","bbbKey");

        boolean aaaKey = redisUtil.hasKey("aaaKey");
        boolean bbbKey = redisUtil.hasKey("bbbKey");
        boolean cccKey = redisUtil.hasKey("cccKey");
        return responseParams.success("20001" , aaaKey + "---" + bbbKey + "---" + cccKey);
    }

}
