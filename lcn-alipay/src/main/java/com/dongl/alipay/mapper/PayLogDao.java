package com.dongl.alipay.mapper;
/**
 * 支付记录数据库操作层
 */

import com.dongl.alipay.entity.PayLog;
import org.springframework.stereotype.Repository;

@Repository
public interface PayLogDao {
	PayLog find(Long id);
	
	PayLog findBySn(String sn);
	
	PayLog findByPaySn(String paySn);
}
