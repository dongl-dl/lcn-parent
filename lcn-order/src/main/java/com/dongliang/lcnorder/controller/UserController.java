package com.dongliang.lcnorder.controller;

import com.dongliang.lcnorder.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description 接口幂等性实现--Token令牌
 * @createTime 2021-05-26 16:40:00
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取 Token 接口
     * @return
     */
    @GetMapping("/getTokenInfo/{uid}")
    public String getTokenInfo(@PathVariable("uid") String uid) {
        return userService.generateToken(uid);
    }

    /**
     * 修改用户账户余额 (账户余额加100.0) Modify user account balance
     * @param token
     * @param uid 用户编码
     * @return 返回调用结果
     */
    @PostMapping("/modifyUserAccountBalance/{uid}")
    public String modifyUserAccountBalance(@RequestHeader(value = "token") String token , @PathVariable("uid") String uid) {
        // 根据 Token 和与用户相关的信息到 Redis 验证是否存在对应的信息
        boolean result = userService.modifyUserAccountBalance(token, uid);
        return result ? "正常调用" : "重复调用";
    }
}
