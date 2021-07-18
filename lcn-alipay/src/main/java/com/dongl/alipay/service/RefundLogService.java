package com.dongl.alipay.service;


import com.dongl.alipay.entity.RefundLog;
import com.dongl.alipay.mapper.RefundLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 后台支付操作service
 */

@Service
public class RefundLogService {
	
	@Autowired
	private RefundLogDao refundLogDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public RefundLog find(Long id){
		return refundLogDao.find(id);
	}
	
	/**
	 * 根据退款编号查询
	 * @param
	 * @return
	 */
	public RefundLog findBySn(String refundSn){
		return refundLogDao.findByRefundSn(refundSn);
	}
	

	/**
	 * 根据支付订单号查询
	 * @param payLogId
	 * @return
	 */
	public List<RefundLog> findByPayLogId(Long payLogId){
		return refundLogDao.findByPayLogId(payLogId);
	}

	public void save(RefundLog refundLog) {

	}
}
