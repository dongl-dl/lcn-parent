package com.dongl.common.entity.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ShoppingCartListVo.java
 * @Description TODO
 * @createTime 2021-08-10 16:49:00
 */
@Data
public class ShoppingCartListVo {
    //用户编码]
    @NotBlank(message = "用户编码不能为空")
    private String uid;

    //应用编码
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    @Range(min = 1 , max = 100 , message = "每页条数最小展示条数为1条,最大展示条数为100条")
    private Integer pageSize;

    @Range(min = 1 , message = "页码最小值为1")
    private Integer pageNum;
}
