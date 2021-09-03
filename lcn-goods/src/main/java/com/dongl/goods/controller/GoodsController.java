package com.dongl.goods.controller;

import com.dongl.common.base.ResponseParams;
import com.dongl.common.entity.vo.ShoppingCartListVo;
import com.dongl.common.entity.vo.ShoppingCartVo;
import com.dongl.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName GoodsController.java
 * @Description TODO
 * @createTime 2021-08-10 16:39:00
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 加入购物车
     * @param shoppingCartVo
     * @return
     */
    @PostMapping("/v1/addToShoppingCart")
    public ResponseParams addToShoppingCart(@Validated @RequestBody ShoppingCartVo shoppingCartVo){
        ResponseParams responseParams = new ResponseParams("加入购物车");
        return goodsService.addToShoppingCart(shoppingCartVo, responseParams);
    }

    /**
     * 移除购物车
     * @param shoppingCartVo
     * @return
     */
    @PostMapping("/v1/removeShoppingCart")
    public ResponseParams removeShoppingCart(@Validated @RequestBody ShoppingCartVo shoppingCartVo){
        ResponseParams responseParams = new ResponseParams("移除购物车");
        return goodsService.removeShoppingCart(shoppingCartVo, responseParams);
    }

    /**
     * 购物车列表
     * @param shoppingCartListVo
     * @return
     */
    @PostMapping("/v1/shoppingCartList")
    public ResponseParams shoppingCartList(@Validated @RequestBody ShoppingCartListVo shoppingCartListVo){
        ResponseParams reponse = new ResponseParams("购物车列表");
        Map<String, Object> shoppingCartListDtoPageInfo = goodsService.shoppingCartList(shoppingCartListVo);
        return reponse.success(shoppingCartListDtoPageInfo);
    }
}
