package com.dongliang.lcnorder.service.impl;

import com.dongliang.lcnorder.config.lock.DistributedLock;
import com.dongliang.lcnorder.dao.TblOrderDao;
import com.dongliang.lcnorder.dao.UsersMapper;
import com.dongliang.lcnorder.entity.TblOrder;
import com.dongliang.lcnorder.entity.Users;
import com.dongliang.lcnorder.service.OrderService;
import com.dongliang.lcnorder.dao.UserDao;
import com.dongliang.lcnorder.entity.User;
import com.dongliang.lcnorder.util.ThreadPoolManager;
import com.dongliang.lcnorder.util.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName OrderServiceImpl.java
 * @Description TODO
 * @createTime 2021-05-17 14:03:00
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    public static AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private UserDao userDao;

    @Autowired
    private TblOrderDao orderDao;

    @Autowired
    private DistributedLock Locker;

    @Autowired
    private UsersMapper usersMapper;
    @Override
    @Async("asyncServiceExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void executeAsync() {
        User user = User.builder().nickName("线程异步测试").pwd("123456").mobile("15566545531").regtime(new Date()).balance(98.4).type(true).build();
        userDao.insert(user);
        System.out.println(String.format("打印当前线程名称：%s", Thread.currentThread().getName()));
    }

    @Override
    public List<Users> getUser() {
        List<Users> userList = usersMapper.getUsers();
        if(userList == null || userList.size() == 0){
            return new ArrayList<>();
        }
        return userList;
    }

    @Override
    public String insertUserByThreadPool() throws Exception {
        Future<String> submit = ThreadPoolManager.getInstance().submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                User user = User.builder().nickName("线程异步测试").pwd("123456").mobile("15566545531").regtime(new Date()).balance(98.4).type(true).build();
                userDao.insert(user);
                System.out.println(String.format("打印当前线程名称：%s", Thread.currentThread().getName()));
                return "OK";
            }
        });

        String result = null;
        try {
            result = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String insertUserByNormal() {
        User user = User.builder().nickName("线程异步测试").pwd("123456").mobile("15566545531").regtime(new Date()).balance(98.4).type(true).build();
        userDao.insert(user);
        System.out.println(String.format("打印当前线程名称：%s", Thread.currentThread().getName()));
        return "OK";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(TblOrder order) {
        log.info("createOrder --- beginning:" + order.getOrderName());
        if (order == null) {
            return;
        }
        try {
            //加分布式锁，保证操作幂等性
            Locker.lock(order.getOrderName() + order.getUid() + order.getPayType()  , 30);
            //创建订单
            buildOrder(order);
            log.info("createOrder --- success:" + order.getOrderName());
        }catch (Exception e) {
            throw new CustomizeException("createOrder --- error");
        }finally {
            Locker.unlock(order.getOrderName() + order.getUid() + order.getPayType());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    protected void buildOrder( TblOrder order) {
        //构建订单明细并补充订单数据
        order.setCreateTime(new Date());
        orderDao.insert(order);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
