package com.dongl.payparent.service;

import com.dongl.payparent.domain.alipay.Product;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ProductService.java
 * @Description TODO
 * @createTime 2021-09-16 10:54:00
 */
public interface ProductService {

    /**
     * 获取所有产品列表
     * @return
     */
    public List<Product> getProducts();

    /**
     * 根据产品ID查询产品详情
     * @param productId
     * @return
     */
    public Product getProductById(String productId);
}
