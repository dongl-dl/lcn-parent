package com.dongl.common.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ShoppingCartVo.java
 * @Description TODO
 * @createTime 2021-08-10 16:41:00
 */
@Data
public class ShoppingCartVo {

    //用户编码
    @NotBlank(message = "用户编码不能为空")
    private String uid;

    //应用编码
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    //图书商品编码集合
    @Size(min = 1, message = "图书商品编码集合不能为空")
    @NotNull(message = "图书商品编码集合不能为空")
    private List<String> bookCodeList;
}
