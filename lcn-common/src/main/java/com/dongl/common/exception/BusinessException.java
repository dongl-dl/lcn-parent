package com.dongl.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName BusinessException.java
 * @Description TODO
 * @createTime 2021-07-20 16:58:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    /**
     * 错误状态码
     */
    protected String errorCode;
    /**
     * 错误提示
     */
    protected String errorMsg;

    /**
     * 报错（微服务、类名、接口、方法）名称  示例：获取异常接口
     */
    protected String remark = "";

    public BusinessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
