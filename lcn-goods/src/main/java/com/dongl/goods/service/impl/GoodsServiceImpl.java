package com.dongl.goods.service.impl;

import com.dongl.common.base.ResponseParams;
import com.dongl.common.entity.vo.ShoppingCartListVo;
import com.dongl.common.entity.vo.ShoppingCartVo;
import com.dongl.common.exception.BusinessException;
import com.dongl.goods.service.GoodsService;
import com.dongl.goods.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dongl.common.constant.ErrorCode.*;
import static com.dongl.common.constant.RedisConstant.REDIS_SHOPPING_CART;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName GoodsServiceImpl.java
 * @Description TODO
 * @createTime 2021-08-10 16:43:00
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private RedisUtil redis;

    @Override
    public ResponseParams addToShoppingCart(ShoppingCartVo shoppingCartVo, ResponseParams responseParams) {
        String uid = shoppingCartVo.getUid();
        String appCode = shoppingCartVo.getAppCode();
        //加入购物车图书编码
        List<String> bookCodeList = shoppingCartVo.getBookCodeList();
        //去除重复数据
        List<String> bookCodes = bookCodeList.stream().distinct().collect(Collectors.toList());
        Map<String, Object> shoppingCartMap = new HashMap<>();
        try {
            String key = String.format("%s%s:%s", REDIS_SHOPPING_CART , uid , appCode);
            bookCodes.stream().forEach(bookCode -> {
                shoppingCartMap.put(bookCode , "1");
            });
            redis.hmset(key, shoppingCartMap);
        }catch (Exception e) {
            log.error("图书加入购物车失败【uid】：{} ,【appCode】:{} ,【bookCode】:{} ,【error】:{}"
                    ,uid ,appCode ,bookCodes ,e.getMessage());
            throw new BusinessException(CART_ADD_ERROR_CODE , CART_ADD_ERROR_DESC);
        }
        return responseParams.success("添加购物车成功");
    }

    @Override
    public ResponseParams removeShoppingCart(ShoppingCartVo shoppingCartVo, ResponseParams responseParams) {
        String uid = shoppingCartVo.getUid();
        String appCode = shoppingCartVo.getAppCode();
        //加入购物车图书编码
        List<String> bookCodeList = shoppingCartVo.getBookCodeList();
        Long removeNum = 0L;
        try {
            String key = String.format("%s%s:%s", REDIS_SHOPPING_CART, uid, appCode);
            String[] bookCodes = bookCodeList.toArray(new String[0]);
            //批量移除购物车中的商品编码操作
            removeNum = redis.hdel(key, bookCodes);
        }catch (Exception e) {
            log.error("批量移除购物车中的商品操作失败【uid】：{} ,【appCode】:{} ,【bookCode】:{} ,【error】:{}"
                    ,uid ,appCode ,bookCodeList ,e.getMessage());
            throw new BusinessException(REMOVE_CART_FAILED_CODE , REMOVE_CART_FAILED_DESC);
        }
        return responseParams.success("成功移除购物车中" + removeNum + "本图书");
    }

    @Override
    public Map<String, Object> shoppingCartList(ShoppingCartListVo shoppingCartListVo) {
        String uid = shoppingCartListVo.getUid();
        String appCode = shoppingCartListVo.getAppCode();
        //获取购物车商品信息对应物品编码
        List<Object> bookCodes = getTheShoppingCartByApp(uid, appCode);
        //todo 购物车不为空 获取购物车列表

        return null;
    }

    /**
     * 根据账户uid和应用编码appCode获取购物车商品信息对应物品编码 （字符拼接）
     * 从缓存中获取
     * @param uid 用户编码
     * @param appCode 应用编码
     * @return
     */
    public List<Object> getTheShoppingCartByApp(String uid , String appCode){
        List<Object> bookCodes = new ArrayList<>();
        try {
            if (!StringUtils.isAnyBlank(uid, appCode)) {
                String key = String.format("%s%s:%s", REDIS_SHOPPING_CART, uid, appCode);
                //获取购物车缓存数据
                Map<Object, Object> shoppingCartMap = redis.hmget(key);
                //获取所有图书编码集合
                if(!shoppingCartMap.isEmpty()) {
                    bookCodes = shoppingCartMap.keySet().stream()
                            .filter(bookCode -> StringUtils.isNotBlank(bookCode.toString())).collect(Collectors.toList());
                }
            }
        }catch (Exception e) {
            log.error("从缓存中获取购物车信息失败--【uid】：{} , 【appCode】：{}" , uid , appCode);
            throw new BusinessException(GET_CART_FAILED_CODE ,GET_CART_FAILED_DESC);
        }
        return bookCodes;
    }
}
