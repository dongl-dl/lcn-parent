package com.dongliang.lcnpay.dao;

import com.dongliang.lcnpay.entity.UserLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLevelDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLevel record);

    int insertSelective(UserLevel record);

    UserLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLevel record);

    int updateByPrimaryKey(UserLevel record);
}