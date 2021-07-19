package com.dongl.alipay.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongl.alipay.entity.*;
import com.dongl.alipay.paytype.Alipay;
import com.dongl.alipay.service.PayLogService;
import com.dongl.alipay.service.RefundLogService;
import com.dongl.alipay.utils.StringUtil;
import com.dongl.alipay.utils.ValidateEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 支付记录管理控制器
 * @author Administrator
 *
 */
@RequestMapping("/pay_log")
@Controller
@Slf4j
public class PayLogController {

	@Autowired
	private PayLogService payLogService;
	@Autowired
	private RefundLogService refundLogService;
	

	
	/**
	 * 支付记录列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, PayLog payLog, PageBean<PayLog> pageBean){
//		model.addAttribute("pageBean", payLogService.findList(payLog, pageBean));
		model.addAttribute("sn", payLog.getSn());
		return "admin/pay_log/list";
	}
	
	/**
	 * 支付记录表单提交
	 * @param
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Result<String> add(PayLog payLog){
		if(payLog == null){
			return Result.error(CodeMsg.DATA_ERROR);
		}
		CodeMsg validate = ValidateEntityUtil.validate(payLog);
		if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
			return Result.error(validate);
		}
		//到这表示一切符合，可以保存到数据库
		//生成支付编号
		payLog.setSn(StringUtil.generateSn("YLRC", ""));
		payLogService.save(payLog);
		return Result.success(payLog.getSn());
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute("paymentList", Payment.values());
		return "admin/pay_log/add";
	}
	
	/**
	 * 发起退款页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/refund",method=RequestMethod.GET)
	public String refund(@RequestParam(name="sn",required=true)String sn,Model model){
		model.addAttribute("payLog", payLogService.findBySn(sn));
		return "admin/pay_log/refund";
	}
	
	/**
	 * 查看订单详情
	 * @param sn
	 * @return
	 */
	@RequestMapping(value="/view",method=RequestMethod.POST)
	@ResponseBody
	public Result<String> view(@RequestParam(name="sn",required=true)String sn){
		PayLog payLog = payLogService.findBySn(sn);
		if(payLog == null){
			return Result.error(CodeMsg.DATA_ERROR);
		}
		//调用支付宝接口进行查看
		String viewDetail = Alipay.viewDetail(sn);
		log.info(viewDetail);
		return Result.success(viewDetail);
	}
	
	/**
	 * 关闭订单请求
	 * @param sn
	 * @return
	 */
	@RequestMapping(value="/close_pay",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> closePay(@RequestParam(name="sn",required=true)String sn){
		PayLog payLog = payLogService.findBySn(sn);
		if(payLog == null){
			return Result.error(CodeMsg.DATA_ERROR);
		}
		if(payLog.getStatus() != PayLog.pay_status_waiting){
			return Result.error(CodeMsg.PAY_CLOSE_ERROR);
		}
		//调用支付宝接口
		String closePay = Alipay.closePay(sn);
		log.info(closePay);
		JSONObject parseObject = JSONObject.parseObject(closePay);
		int code = parseObject.getJSONObject("alipay_trade_close_response").getIntValue("code");
		if(code != 10000){
			CodeMsg codeMsg = CodeMsg.PAY_CLOSE_ERROR;
			codeMsg.setMsg(parseObject.getJSONObject("alipay_trade_close_response").getString("sub_msg"));
			return Result.error(codeMsg);
		}
		payLog.setStatus(PayLog.pay_status_closed);
		payLogService.save(payLog);
		return Result.success(true);
	}
	
	/**
	 * 退款请求提交
	 * @param refundLog
	 * @return
	 */
	@RequestMapping(value="/refund",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> refund(RefundLog refundLog){
		if(refundLog == null){
			return Result.error(CodeMsg.DATA_ERROR);
		}
		PayLog existPayLog = payLogService.find(refundLog.getPayLog().getId());
		if(existPayLog == null){
			return Result.error(CodeMsg.DATA_ERROR);
		}
		if(existPayLog.getStatus() != PayLog.pay_status_paid && existPayLog.getStatus() != PayLog.pay_status_part_refunded){
			return Result.error(CodeMsg.PAY_REFUND_ERROR);
		}
		//再次检查金额是否合法
		BigDecimal avaliableRefund = existPayLog.getTotalAmount().subtract(existPayLog.getRefundAmount());
		if(refundLog.getRefundAmount().compareTo(avaliableRefund) > 0){
			CodeMsg codeMsg = CodeMsg.PAY_REFUND_ERROR;
			codeMsg.setMsg("可退金额不得超过" + avaliableRefund);
			return Result.error(codeMsg);
		}
		refundLog.setRefundSn(StringUtil.generateSn("REFUND", ""));
		//调用支付宝退款接口
		String refund = Alipay.refund(existPayLog.getSn(), refundLog.getRefundAmount(), refundLog.getRefundInfo(), refundLog.getRefundSn());
		log.info(refund);
		JSONObject parseObject = JSONObject.parseObject(refund);
		int code = parseObject.getJSONObject("alipay_trade_refund_response").getIntValue("code");
		if(code != 10000){
			CodeMsg codeMsg = CodeMsg.PAY_CLOSE_ERROR;
			codeMsg.setMsg(parseObject.getJSONObject("alipay_trade_refund_response").getString("sub_msg"));
			return Result.error(codeMsg);
		}
		existPayLog.setRefundAmount(existPayLog.getRefundAmount().add(refundLog.getRefundAmount()));
		if(existPayLog.getRefundAmount().compareTo(existPayLog.getTotalAmount()) == 0){
			existPayLog.setStatus(PayLog.pay_status_refunded);
		}else{
			existPayLog.setStatus(PayLog.pay_status_part_refunded);
		}
		existPayLog.setRefundTime(new Date());
		refundLogService.save(refundLog);
		payLogService.save(existPayLog);
		return Result.success(true);
	}
	
	/**
	 * 查看退款详情
	 * @param sn
	 * @return
	 */
	@RequestMapping(value="/view_refund",method=RequestMethod.POST)
	@ResponseBody
	public Result<List<RefundLog>> viewRefund(@RequestParam(name="sn",required=true)String sn){
		PayLog payLog = payLogService.findBySn(sn);
		if(payLog == null){
			return Result.error(CodeMsg.DATA_ERROR);
		}
		return Result.success(refundLogService.findByPayLogId(payLog.getId()));
	}
	
	/**
	 * 下载账单
	 * @param billDate
	 * @return
	 */
	@RequestMapping(value="/download_bill",method=RequestMethod.POST)
	@ResponseBody
	public Result<String> download(@RequestParam(name="billDate",required=true)String billDate){
		//调用支付宝接口进行查看
		String download = Alipay.downloadBill("trade", billDate);
		log.info(download);
		JSONObject parseObject = JSONObject.parseObject(download);
		JSONObject downloadObject = parseObject.getJSONObject("alipay_data_dataservice_bill_downloadurl_query_response");
		int code = downloadObject.getIntValue("code");
		if(code != 10000){
			CodeMsg codeMsg = CodeMsg.PAY_DOWNLOAD_BILL_ERROR;
			codeMsg.setMsg(downloadObject.getString("sub_msg"));
			return Result.error(codeMsg);
		}
		return Result.success(downloadObject.getString("bill_download_url"));
	}
}
