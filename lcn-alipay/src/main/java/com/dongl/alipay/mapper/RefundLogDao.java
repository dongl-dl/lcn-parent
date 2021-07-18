package com.dongl.alipay.mapper;
/**
 * 退款记录数据库操作层
 */


import com.dongl.alipay.entity.RefundLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundLogDao {
	RefundLog find(Long id);
	
	RefundLog findByRefundSn(String refundSn);
	
	List<RefundLog> findByPayLogId(Long payLogId);
}
