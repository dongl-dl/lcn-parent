package com.dongliang.lcnorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.dongliang.lcnorder.api.UserClient;
import com.dongliang.lcnorder.entity.Users;
import com.dongliang.lcnorder.entity.dto.UserListDto;
import com.dongliang.lcnorder.service.OrderService;
import com.dongliang.lcnorder.dao.TblOrderDao;
import com.dongliang.lcnorder.dao.UserDao;
import com.dongliang.lcnorder.entity.TblOrder;
import com.dongliang.lcnorder.entity.User;
import com.dongliang.lcnorder.util.pagehelper.PageResult;
import com.dongliang.lcnorder.util.pagehelper.PageUtils;
import com.dongliang.lcnorder.util.response.ResponseParams;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private TblOrderDao tblOrderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderService orderService;



    public OrderController() {
    }

    @PostMapping("/add-order")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String add(@RequestBody TblOrder bean){

        JSONObject date = new JSONObject();
        date.put("payName",bean.getOrderName()+"pay");

        restTemplate.postForEntity("http://lcn-pay/add-pay",date,String.class);
        int i = 1/0;
        tblOrderDao.insert(bean);
        return "新增订单成功";
    }

    @PostMapping("/add-order1")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String addNew(@RequestBody User user){

        JSONObject date = new JSONObject();
        date.put("levelname",user.getNickName()+"pay");
        String s = userClient.addPay1(date);
        System.out.println(s);
//        int i = 1/0;
        userDao.insert(user);
        return "新增订单成功";
    }


//If fewer than corePoolSize threads are running, try to start a new thread with the given command as its first task


    @GetMapping("/testThreadPool")
    public String testThreadPool(){
        orderService.executeAsync();
        return "success------";
    }

    /**
     * 获取用户列表
     * @param userListDto 入参
     * @return
     */
    @PostMapping("/getUser")
    public ResponseParams getUser(@Validated @RequestBody UserListDto userListDto){
        ResponseParams responseParams = new ResponseParams("查询用户列表");
        PageHelper.startPage(userListDto.getPageNum(),userListDto.getPageSize());
        List<Users> users = orderService.getUser();
        PageResult<List<Users>> pageResult = PageUtils.build(users);
        return responseParams.success("200" , pageResult);
    }

    /**
     * 线程池异步执行业务
     * @param bean
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/insertUserByThreadPool")
    public String insertUserByThreadPool() throws Exception {
        String result = orderService.insertUserByThreadPool();
        return result;
    }

    @GetMapping("/insertUserByNormal")
    public String insertUserByNormal(){
        String result = orderService.insertUserByNormal();
        return result;
    }

    @PostMapping("/createOrder")
    public String createOrder(){
        TblOrder order = TblOrder.builder().orderName("redission pay").uid("U202106048214").payType("ALIPAY").build();
        orderService.createOrder(order);
        return "success";
    }

    @GetMapping("/test-order")
    public String testOrder(){
        return "test-----order";
    }
}
