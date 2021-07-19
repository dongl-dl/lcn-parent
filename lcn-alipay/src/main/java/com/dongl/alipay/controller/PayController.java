package com.dongl.alipay.controller;
/**
 * 支付统一处理控制器
 */


import com.dongl.alipay.constant.RuntimeConstant;
import com.dongl.alipay.entity.PayLog;
import com.dongl.alipay.entity.Payment;
import com.dongl.alipay.paytype.Alipay;
import com.dongl.alipay.service.PayLogService;
import com.dongl.alipay.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;


@RequestMapping("/pay")
@Controller
@Slf4j
public class PayController {

	@Autowired
	private PayLogService payLogService;
	
	/**
	 * 支付统一入口方法
	 * @param sn
	 * @param model
	 * @return
	 */
	@RequestMapping("/to_pay")
	public String toPay(@RequestParam(name="sn",required=true)String sn,Model model,HttpServletRequest request){
		PayLog payLog = payLogService.findBySn(sn);
		if(payLog == null){
			model.addAttribute("msg", "订单编号不存在！");
			return RuntimeConstant.RUNTIME_ERROR_VIEW;
		}
		//根据支付记录来判断支付方式
		if(payLog.getPayment() == 1){
			//表示是支付宝支付
			String html = null;
			//判断浏览器是手机还是pc
			if(StringUtil.isMobile(request)){
				//表示是手机浏览器
				html = Alipay.generateWapPayHtml(payLog.getSn(), payLog.getTotalAmount(), payLog.getTitle(), payLog.getInfo());
			}else{
				html = Alipay.generatePcPayHtml(payLog.getSn(), payLog.getTotalAmount(), payLog.getTitle(), payLog.getInfo());
			}
			model.addAttribute("html", html);
			return "pay/alipay/alipay_pc";
		}
		//其他支付
		return RuntimeConstant.RUNTIME_ERROR_VIEW;
	}
	
	/**
	 * 支付宝异步通知接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/alipay_notify")
	@ResponseBody
	public String alipayNotify(HttpServletRequest request){
		//检查异步通知的签名是否合法
		log.info("进入支付宝异步通知接口！");
		if(!Alipay.isValid(request)){
			log.error("支付宝签名验证失败！");
			return "fail";
		}
		//表示签名验证成功
		//订单号
		String sn = request.getParameter("out_trade_no");
		//支付宝交易号
		String paySn = request.getParameter("trade_no");
		//支付金额
		String totalAmount = request.getParameter("total_amount");
		//支付状态
		String status = request.getParameter("trade_status");
		if(StringUtils.isEmpty(sn)){
			log.error("订单编号为空！");
			return "fail";
		}
		if(StringUtils.isEmpty(paySn)){
			log.error("支付宝交易号为空！");
			return "fail";
		}
		if(StringUtils.isEmpty(totalAmount)){
			log.error("支付金额为空！");
			return "fail";
		}
		if(StringUtils.isEmpty(status)){
			log.error("支付状态为空！");
			return "fail";
		}
		//到这表示参数都是合法的
		PayLog payLog = payLogService.findBySn(sn);
		if(payLog == null){
			log.error("订单编号不存在！【" + sn + "】");
			return "fail";
		}
		if(payLog.getTotalAmount().compareTo(new BigDecimal(totalAmount)) != 0){
			log.error("支付金额不同！【" + totalAmount + "】" + "【" + payLog.getTotalAmount() + "】");
			return "fail";
		}
		if(!status.equals("TRADE_SUCCESS")){
			log.info("订单状态非成功！【" + status + "】");
			return "fail";
		}
		//表示一切合法
		if(payLog.getStatus() == PayLog.pay_status_waiting){
			payLog.setPaySn(paySn);
			payLog.setStatus(PayLog.pay_status_paid);
			payLog.setPayTime(new Date());
			payLogService.save(payLog);
		}
		return "success";
	}
}
