package com.dongl.payparent.service;

import com.dongl.payparent.domain.alipay.Orders;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName OrdersService.java
 * @Description TODO
 * @createTime 2021-09-16 10:56:00
 */
public interface OrdersService {

    /**
     * 新增订单
     * @param order
     */
    public void saveOrder(Orders order);

    /**
     *
     * @Title: OrdersService.java
     * @Package com.sihai.service
     * @Description: 修改订单状态，改为 支付成功，已付款; 同时新增支付流水
     */
    public void updateOrderStatus(String orderId, String alpayFlowNum, String paidAmount);

    /**
     * 获取订单
     * @param orderId
     * @return
     */
    public Orders getOrderById(String orderId);
}
