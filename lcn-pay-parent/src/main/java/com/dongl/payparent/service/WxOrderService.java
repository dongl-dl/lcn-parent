package com.dongl.payparent.service;

import com.dongl.payparent.domain.wxpay.PayResult;
import com.dongl.payparent.domain.wxpay.PreOrderResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName WxOrderService.java
 * @Description TODO
 * @createTime 2021-09-16 16:46:00
 */
public interface WxOrderService {

    /**
     * 调用微信接口进行统一下单
     * @param body
     * @param out_trade_no
     * @param total_fee
     * @return
     * @throws Exception
     */
    public PreOrderResult placeOrder(String body, String out_trade_no, String total_fee) throws Exception;

    /**
     * 获取支付结果
     * @param request
     * @return
     * @throws Exception
     */
    public PayResult getWxPayResult(HttpServletRequest request) throws Exception;
}
