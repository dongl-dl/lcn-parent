package com.dongl.alipay.mapper;

import com.dongl.alipay.entity.PayLog;
import com.dongl.alipay.entity.RefundLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(PayLog record);

    int insertSelective(PayLog record);

    int updateByPrimaryKeySelective(PayLog record);

    int updateByPrimaryKey(PayLog record);

    RefundLog findRefundLog(Long id);

    PayLog findPayLog(Long id);

    PayLog findBySn(String sn);

    PayLog findByPaySn(String paySn);

    RefundLog findByRefundSn(String refundSn);

    List<RefundLog> findByPayLogId(Long payLogId);
}