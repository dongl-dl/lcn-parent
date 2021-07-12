package com.dl.user.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ResponseControllerAdvice.java
 * @Description 自定义通用异常
 * @createTime 2021-06-16 09:37:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class CustomizeException extends RuntimeException {

    private static final long serialVersionUID = -1514051287932579564L;

    private  String code;

    private  String msg;

    private  String description;

    public CustomizeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomizeException(String code, String msg, String description) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.description = description;
    }
}