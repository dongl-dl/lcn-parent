package com.dongl.goods.service;

import com.dongl.common.base.ResponseParams;
import com.dongl.common.entity.vo.ShoppingCartListVo;
import com.dongl.common.entity.vo.ShoppingCartVo;

import java.util.Map;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName GoodsService.java
 * @Description TODO
 * @createTime 2021-08-10 16:42:00
 */
public interface GoodsService {
    ResponseParams addToShoppingCart(ShoppingCartVo shoppingCartVo, ResponseParams responseParams);

    ResponseParams removeShoppingCart(ShoppingCartVo shoppingCartVo, ResponseParams responseParams);

    Map<String, Object> shoppingCartList(ShoppingCartListVo shoppingCartListVo);

}
