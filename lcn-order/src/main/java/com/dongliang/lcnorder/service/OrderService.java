package com.dongliang.lcnorder.service;

import com.dongliang.lcnorder.entity.TblOrder;
import com.dongliang.lcnorder.entity.User;
import com.dongliang.lcnorder.entity.Users;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName OrderService.java
 * @Description TODO
 * @createTime 2021-05-17 14:01:00
 */
public interface OrderService {

    /**
     * 执行异步任务
     * 可以根据需求，自己加参数拟定，我这里就做个测试演示
     */
    void executeAsync();


    List<Users> getUser();

    String insertUserByThreadPool() throws Exception;

    String insertUserByNormal();

    void createOrder(TblOrder order);
}
