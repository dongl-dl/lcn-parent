package com.dongliang.lcnpay.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.dongliang.lcnpay.dao.TblPayDao;
import com.dongliang.lcnpay.dao.UserLevelDao;
import com.dongliang.lcnpay.entity.TblPay;
import com.dongliang.lcnpay.entity.UserLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class PayController {


    @Autowired
    private TblPayDao tblPayDao;

    @Autowired
    private UserLevelDao userLevelDao;

    @PostMapping("/add-pay")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String addPay(@RequestBody TblPay bean){
        tblPayDao.insert(bean);
        return "新增支付成功";

    }

    @PostMapping("/add-pay1")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String addPay1(@RequestBody UserLevel bean){
        String levelname = bean.getLevelname();
        bean.setLastupdate(new Date());
        bean.setLeveldesc("123");
        userLevelDao.insert(bean);
//        int i = 1/0;
        System.out.println("chenggong --------------------------");
        return "新增支付成功";

    }
}
