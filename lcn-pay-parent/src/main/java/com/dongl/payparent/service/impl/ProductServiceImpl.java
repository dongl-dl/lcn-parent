package com.dongl.payparent.service.impl;

import com.dongl.payparent.domain.alipay.Product;
import com.dongl.payparent.mapper.IProductdao;
import com.dongl.payparent.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ProductServiceImpl.java
 * @Description 商品实现层
 * @createTime 2021-09-16 10:58:00
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private IProductdao iProductdao;


    @Override
    public List<Product> getProducts() {
        return iProductdao.getAllProduct();
    }

    @Override
    public Product getProductById(String productId) {
        return iProductdao.getProductById(productId);
    }
}
