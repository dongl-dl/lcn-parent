package com.dongl.payparent.mapper;

import com.dongl.payparent.domain.alipay.Flow;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName IFlowDao.java
 * @Description TODO
 * @createTime 2021-09-16 11:02:00
 */
@Mapper
public interface IFlowDao {

    void insert(Flow flow);
}
