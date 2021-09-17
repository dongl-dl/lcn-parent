package com.dongl.payparent.service.impl;

import com.dongl.payparent.content.OrderStatusEnum;
import com.dongl.payparent.domain.alipay.Flow;
import com.dongl.payparent.domain.alipay.Orders;
import com.dongl.payparent.mapper.IFlowDao;
import com.dongl.payparent.mapper.IOrderDao;
import com.dongl.payparent.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName OrderServiceImpl.java
 * @Description TODO
 * @createTime 2021-09-16 11:01:00
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private IOrderDao iOrderDao;

    @Autowired
    private IFlowDao iFlowDao;


    @Override
    public void saveOrder(Orders order) {
        iOrderDao.insert(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderId, String alpayFlowNum, String paidAmount) {
        Orders order = getOrderById(orderId);
        //如果是 待付款 修改订单状态 并且 并且 新增  流水
        if (order.getOrderStatus().equals(OrderStatusEnum.WAIT_PAY.key)) {
            order = new Orders();
            order.setId(orderId);
            order.setOrderStatus(OrderStatusEnum.PAID.key);
            order.setPaidTime(new Date());
            order.setPaidAmount(paidAmount);

            iOrderDao.updateById(order);

            //查询    修改后最新的order
            order = getOrderById(orderId);
            String flowId = UUID.randomUUID().toString();
            Flow flow = new Flow();
            flow.setId(flowId);
            flow.setFlowNum(alpayFlowNum);
            flow.setBuyCounts(order.getBuyCounts());
            flow.setCreateTime(new Date());
            flow.setOrderNum(orderId);
            flow.setPaidAmount(paidAmount);
            flow.setPaidMethod(1);
            flow.setProductId(order.getProductId());
            //插入 流水表
            iFlowDao.insert(flow);

        }

    }

    @Override
    public Orders getOrderById(String orderId) {
        log.info("开始查询订单，订单orderId:{}", orderId);
        return iOrderDao.getOrderById(orderId);
    }
}
