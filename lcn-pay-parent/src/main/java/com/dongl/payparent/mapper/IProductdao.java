package com.dongl.payparent.mapper;

import com.dongl.payparent.domain.alipay.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName IProductdao.java
 * @Description TODO
 * @createTime 2021-09-16 10:59:00
 */

@Mapper
public interface IProductdao {

    @Select("select * from product ")
    List<Product> getAllProduct();

    @Select("select * from product where id=#{id}")
    Product getProductById(String id);


}
