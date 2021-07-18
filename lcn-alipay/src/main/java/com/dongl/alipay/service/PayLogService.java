package com.dongl.alipay.service;


import com.dongl.alipay.entity.PageBean;
import com.dongl.alipay.entity.PayLog;
import com.dongl.alipay.mapper.PayLogDao;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * 后台支付操作service
 */

@Service
public class PayLogService {
	
	@Autowired
	private PayLogDao payLogDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public PayLog find(Long id){
		return payLogDao.find(id);
	}
	
	/**
	 * 根据支付订单编号查询
	 * @param sn
	 * @return
	 */
	public PayLog findBySn(String sn){
		return payLogDao.findBySn(sn);
	}
	
	/**
	 * 根据第三方支付平台交易号查询
	 * @param paySn
	 * @return
	 */
	public PayLog findByPaySn(String paySn){
		return payLogDao.findByPaySn(paySn);
	}

	public void save(PayLog payLog) {

	}
}
