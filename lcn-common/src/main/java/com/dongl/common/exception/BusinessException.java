package com.dongl.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName BusinessException.java
 * @Description TODO
 * @createTime 2021-07-22 15:11:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    protected String errorCode;

    protected String errorMessage;

    public BusinessException( String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
