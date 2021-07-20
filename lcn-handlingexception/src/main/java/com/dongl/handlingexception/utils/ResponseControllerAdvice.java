package com.dongl.handlingexception.utils;

import com.dongl.handlingexception.entity.ErrorEnum;
import com.dongl.handlingexception.entity.StatusCode;
import com.dongl.handlingexception.exception.CustomizeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ResponseControllerAdvice.java
 * @Description 处理全局异常
 * @createTime 2021-06-16 09:37:00
 */

@RestControllerAdvice
public class ResponseControllerAdvice {

    /**
     * 处理方法参数无效异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseParams MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ResponseParams<Object> responseParams = new ResponseParams<>();
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        String defaultMessage = objectError.getDefaultMessage();
        return responseParams.error(StatusCode.ERROR,defaultMessage);
    }

    /**
     * 处理自定义异常
     * @param exceptionMsg
     * @return
     */
    @ExceptionHandler(value = CustomizeException.class)
    public ResponseParams customExceptionsHandler(CustomizeException exceptionMsg) {
        String code = exceptionMsg.getCode();
        String msg = exceptionMsg.getMsg();
        String description = exceptionMsg.getDescription();
        ResponseParams<Object> responseParams = new ResponseParams<>(description);
        return responseParams.error(code ,msg);
    }

    /**
     * 处理其他异常
     * @param exceptionMsg
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseParams exceptionsHandler(Exception exceptionMsg) {
        ResponseParams<Object> responseParams = new ResponseParams<>();
        return responseParams.error(ErrorEnum.INTERNAL_SERVER_ERROR.getErrorCode() ,ErrorEnum.INTERNAL_SERVER_ERROR.getErrorMsg());
    }
}