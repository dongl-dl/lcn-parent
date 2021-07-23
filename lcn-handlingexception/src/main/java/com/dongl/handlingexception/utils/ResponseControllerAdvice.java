package com.dongl.handlingexception.utils;

import com.dongl.handlingexception.entity.ErrorEnum;
import com.dongl.handlingexception.entity.StatusCode;
import com.dongl.handlingexception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ResponseControllerAdvice.java
 * @Description 处理全局异常
 * @createTime 2021-06-16 09:37:00
 */
@Slf4j
@RestControllerAdvice
public class ResponseControllerAdvice {

    /**
     * 处理方法参数无效异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseParams MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ResponseParams<Object> responseParams = new ResponseParams<>("方法参数无效");
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        String defaultMessage = objectError.getDefaultMessage();
        return responseParams.error(StatusCode.ERROR,defaultMessage);
    }


    /**
     * 参数格式异常处理
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseParams badRequestException(IllegalArgumentException e) {
        ResponseParams<Object> responseParams = new ResponseParams<>();
        log.error("参数格式不合法：" + e.getMessage());
        return responseParams.error(ErrorEnum.PARAMETER_FORMAT_ERROR.getErrorCode() ,ErrorEnum.PARAMETER_FORMAT_ERROR.getErrorMsg());
    }

    /**
     * 权限不足异常处理
     * @param e
     * @return
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseParams badRequestException(AccessDeniedException e) {
        ResponseParams<Object> responseParams = new ResponseParams<>();
        log.error("权限不足：" + e.getMessage());
        return responseParams.error(ErrorEnum.NO_PERMISSION.getErrorCode() ,ErrorEnum.NO_PERMISSION.getErrorMsg());
    }


    /**
     * 参数缺失异常处理  Abnormal parameter missing
     * @param e
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseParams badRequestException(Exception e) {
        ResponseParams<Object> responseParams = new ResponseParams<>();
        log.error("参数缺失，{}", e.getMessage());
        return responseParams.error(ErrorEnum.PARAMETER_MISSING_ERROR.getErrorCode() ,ErrorEnum.PARAMETER_MISSING_ERROR.getErrorMsg());
    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseParams customExceptionsHandler(BusinessException e) {
        ResponseParams<Object> responseParams = new ResponseParams<>(e.getRemark());
        log.error("业务异常：{}", e.toString());
        return responseParams.error(e.getErrorCode() ,e.getErrorMsg());
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseParams handleUnexpectedServer(Exception e) {
        ResponseParams<Object> responseParams = new ResponseParams<>();
        log.error("系统异常-----【Message】：{}  ， 【StackTrace】：{}", e.getMessage() , e.getStackTrace()[0]);
        return responseParams.error(ErrorEnum.INTERNAL_SERVER_ERROR.getErrorCode() ,ErrorEnum.INTERNAL_SERVER_ERROR.getErrorMsg());
    }

    /**
     * 系统异常处理
     * @param throwable
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ResponseParams exception(Throwable throwable) {
        ResponseParams<Object> responseParams = new ResponseParams<>();
        log.error("系统异常", throwable);
        return responseParams.error(ErrorEnum.INTERNAL_SERVER_ERROR.getErrorCode() ,ErrorEnum.INTERNAL_SERVER_ERROR.getErrorMsg());
    }
}