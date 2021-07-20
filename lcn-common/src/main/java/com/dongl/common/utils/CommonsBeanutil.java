package com.dongl.common.utils;

import com.dongl.common.entity.User;
import com.dongl.common.entity.UserInfo;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName CommonsBeanutils.java
 * @Description TODO
 * @createTime 2021-07-20 09:26:00
 */
public class CommonsBeanutil {
    public static void main(String[] args) throws Exception {
        User user = new User();
        BeanUtils.setProperty(user, "id", 1);
        BeanUtils.setProperty(user, "name", "yideng");
        System.out.println(BeanUtils.getProperty(user, "name")); // 输出 yideng
        System.out.println(user); // 输出 {"id":1,"name":"yideng"}



        // 对象转map
        Map<String, String> map = BeanUtils.describe(user);
        System.out.println(map); // 输出 {"id":"1","name":"yideng"}
        // map转对象
        User newUser = new User();
        UserInfo userInfo = new UserInfo();
        BeanUtils.populate(newUser, map);
        BeanUtils.copyProperties(userInfo,newUser);
        System.out.println(userInfo); // 输出 {"id":1,"name":"yideng"}
    }
}
