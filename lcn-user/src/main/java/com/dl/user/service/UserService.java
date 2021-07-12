package com.dl.user.service;

import com.dl.user.domain.User;
import com.dl.user.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2021-06-29 15:34:00
 */
@Service
public class UserService {

    @Autowired
    private TbUserMapper userMapper;

    public User findById(String username){
        User user = userMapper.findById(username);
        return user;
    }
}
