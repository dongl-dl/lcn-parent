package com.dongl.handlingexception.exception;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName BusinessException.java
 * @Description TODO
 * @createTime 2021-07-20 16:58:00
 */
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

    public BusinessException(){

    }

    public BusinessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
