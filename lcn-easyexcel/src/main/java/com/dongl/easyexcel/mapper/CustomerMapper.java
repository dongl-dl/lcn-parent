package com.dongl.easyexcel.mapper;


import com.dongl.easyexcel.domian.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


/**
 * @author D-L
 * @program: easy-excel
 * @description:
 * @date 2021-09-14 11:46:18
 */
@Mapper
@Component
public interface CustomerMapper {

    int insertSelective(Customer record);
}