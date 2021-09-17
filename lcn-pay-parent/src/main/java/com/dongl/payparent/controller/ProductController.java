package com.dongl.payparent.controller;

import com.dongl.payparent.content.OrderStatusEnum;
import com.dongl.payparent.domain.alipay.Orders;
import com.dongl.payparent.domain.alipay.Product;
import com.dongl.payparent.service.OrdersService;
import com.dongl.payparent.service.ProductService;
import com.dongl.payparent.utils.LeeJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ProductController.java
 * @Description 商品模块 控制层
 * @createTime 2021-09-16 10:53:00
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersService ordersService;

    /**
     * 获取产品列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping
    public String products(Map map) {
        List<Product> pList = productService.getProducts();
        map.put("pList", pList);
        return "index";
    }

    @RequestMapping("index")
    public String index(Map map) {
        List<Product> pList = productService.getProducts();
        map.put("pList", pList);
        return "index";
    }

    /**
     * 进入确认页面
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goConfirm")
    public String goConfirm(String productId, Map map) {
        Product p = productService.getProductById(productId);
        map.put("p", p);
        return "goConfirm";
    }

    /**
     * 分段提交
     * 第一段：保存订单
     *
     * @param order
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createOrder")
    @ResponseBody
    public LeeJSONResult createOrder(Orders order) throws Exception {

        Product p = productService.getProductById(order.getProductId());
        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);
        order.setOrderNum(orderId);
        order.setCreateTime(new Date());
        order.setOrderAmount(String.valueOf(Float.valueOf(p.getPrice()) * order.getBuyCounts()));
        order.setOrderStatus(OrderStatusEnum.WAIT_PAY.key);
        ordersService.saveOrder(order);

        return LeeJSONResult.ok(orderId);
    }

    /**
     * 分段提交
     * 第二段
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goPay")
    public String goPay(String orderId, Map map) {

        Orders order = ordersService.getOrderById(orderId);

        Product p = productService.getProductById(order.getProductId());

        map.put("order", order);
        map.put("p", p);

        return "goPay";
    }

}
