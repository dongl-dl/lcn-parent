package com.dongl.payparent.controller;

import com.dongl.payparent.domain.alipay.Orders;
import com.dongl.payparent.domain.alipay.Product;
import com.dongl.payparent.domain.wxpay.PayResult;
import com.dongl.payparent.domain.wxpay.PreOrderResult;
import com.dongl.payparent.service.OrdersService;
import com.dongl.payparent.service.ProductService;
import com.dongl.payparent.service.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName WxpayController.java
 * @Description 微信支付控制层
 * @createTime 2021-09-16 16:45:00
 */
@Controller
@RequestMapping("/wxpay")
public class WxpayController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersService orderService;

    @Autowired
    private WxOrderService wxOrderService;


    @RequestMapping(value = "/index")
    public String products() throws Exception {

        return "index";
    }

    // TODO 实际情况需要自己业务订单的状态，此处仅仅用于测试
    public static boolean isOrderPaid = false;

    /**
     * 生成预付单
     * @param orderId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createPreOrder")
    public ModelAndView createPreOrder(String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Orders order = orderService.getOrderById(orderId);

        Product p = productService.getProductById(order.getProductId());

//		ModelAndView mv = new ModelAndView("goPay");
//		mv.addObject("order", order);
//		mv.addObject("p", p);

        // 商品描述
        String body = p.getName();
        // 商户订单号
        String out_trade_no = UUID.randomUUID().toString();
        // 订单总金额，单位为分
        String total_fee = "2";
        // 统一下单
        PreOrderResult preOrderResult = wxOrderService.placeOrder(body, out_trade_no, total_fee);

        ModelAndView mv = new ModelAndView("payQrCode");
        mv.addObject("qrCodeUrl", preOrderResult.getCode_url());

        return mv;
    }

    /**
     *
     * @Description: 扫码完毕后每隔3秒检查是否支付成功
     * @param request
     * @param response
     * @return
     * @throws Exception
     *
     * @author leechenxiang
     * @date 2017年9月1日 上午9:01:09
     */
    @RequestMapping(value = "/wxPayIsSuccess")
    @ResponseBody
    public boolean wxPayIsSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO 查看订单是否支付成功，成功返回true，失败返回false
        return isOrderPaid;
    }

    /**
     *
     * @Description: 微信支付异步通知
     * 					1. 支付成功修改订单状态，标记为成功
     * 					2. 生成支付流水
     */
    @RequestMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        PayResult payResult = wxOrderService.getWxPayResult(request);

        boolean isPaid = payResult.getReturn_code().equals("SUCCESS") ? true : false;

        // 查询该笔订单在微信那边是否成功支付
        // 支付成功，商户处理后同步返回给微信参数
        PrintWriter writer = response.getWriter();
        if (isPaid) {
            System.out.println("================================= 支付成功 =================================");

            // ====================== 操作商户自己的业务，比如修改订单状态，生成支付流水等 start ==========================
            // TODO
            this.isOrderPaid = true;
            // ============================================ 业务结束， end ==================================

            // 通知微信已经收到消息，不要再给我发消息了，否则微信会8连击调用本接口
            String noticeStr = setXML("SUCCESS", "");
            writer.write(noticeStr);
            writer.flush();

        } else {
            System.out.println("================================= 支付失败 =================================");

            // 支付失败
            String noticeStr = setXML("FAIL", "");
            writer.write(noticeStr);
            writer.flush();
        }

    }

    /**
     * 支付成功
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/paySuccess")
    public String paySuccess() throws Exception {
        return "paySuccess";
    }

    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

}
