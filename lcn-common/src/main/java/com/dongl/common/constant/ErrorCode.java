package com.dongl.common.constant;

import java.math.BigDecimal;


public class ErrorCode {

    /****************** 接口调用描述  *****************/
    public static final String SUCCESS_DESCRIBE = "【调用成功】";
    public static final String FAIL_DESCRIBE = "【调用失败】";
    public static final String SUCCESS_ERROR = "网络繁忙，请稍后重试";


    /****************** 系统状态(status) *****************/
    /**
     * 成功
     */
    public static final String STATUS_SUCCESS = "1";
    /**
     * 失败
     */
    public static final String STATUS_FAIL = "0";


    /*************** 系统错误码(code) ******************/
    /**
     * 成功
     */
    public static final String SYSTEM_SUCCESS = "0x00000000";
    /**
     * 系统异常
     */
    public static final String SYSTEM_EXCEPTION = "10000001";
    /**
     * API 不存在
     */
    public static final String API_NOT_EXIST = "10000002";
    /**
     * 参数错误
     */
    public static final String ERROR_PARAM_CODE = "10000003";
    public static final String ERROR_PARAM_DESC = "参数错误：";

    /**
     * 应用管理相关错误信息
     */
    public static final String USER_APP_INFO_IS_BLANK = "20001001";
    public static final String USER_APP_INFO_IS_BLANK_DESC = "应用信息不能为空";
    public static final String CATEGORY_CODE_IS_BLANK = "20001002";
    public static final String CATEGORY_CODE_IS_BLANK_DESC = "应用类别不能为空";
    public static final String USER_APP_NAME_IS_BLANK = "20001003";
    public static final String USER_APP_NAME_IS_BLANK_DESC = "应用名称不能为空";
    public static final String USER_APP_NAME_TOO_LONG = "20001004";
    public static final String USER_APP_NAME_TOO_LONG_DESC = "应用名称不能为空";
    public static final String CATEGORY_CODE_ERROR = "20001005";
    public static final String CATEGORY_CODE_ERROR_DESC = "应用类别错误";
    public static final String USER_ACCESS_METHOD_IS_BLANK = "20001006";
    public static final String USER_ACCESS_METHOD_IS_BLANK_DESC = "接入方式不能为空";
    public static final String USER_ACCESS_METHOD_ERROR = "20001007";
    public static final String USER_ACCESS_METHOD_ERROR_DESC = "接入方式错误";
    public static final String USER_APP_BUNDLE_IS_BLANK = "20001008";
    public static final String USER_APP_BUNDLE_IS_BLANK_DESC = "bundle id 不能为空";
    public static final String USER_APP_PACKAGE_IS_BLANK = "20001009";
    public static final String USER_APP_PACKAGE_IS_BLANK_DESC = "应用包名不能为空";
    public static final String USER_APP_PACKAGE_TOO_LONG = "20001010";
    public static final String USER_APP_PACKAGE_TOO_LONG_DESC = "应用包名不能超过50字";
    public static final String USER_APP_SING_IS_BLANK = "20001011";
    public static final String USER_APP_SING_IS_BLANK_DESC = "应用签名不能为空";
    public static final String USER_APP_SING_ERROR = "20001012";
    public static final String USER_APP_SING_ERROR_DESC = "应用签名错误";
    public static final String USER_APP_DESC_TOO_LONG = "20001013";
    public static final String USER_APP_DESC_TOO_LONG_DESC = "应用描述不能超过200字";
    public static final String USER_APP_CODE_IS_BLANK = "20001014";
    public static final String USER_APP_CODE_IS_BLANK_DESC = "应用编号不能为空";
    public static final String USER_UID_IS_BLANK = "20001015";
    public static final String USER_UID_IS_BLANK_DESC = "uid不能为空";
    public static final String USER_APP_STATUS_UPDATE_ERROR = "20001016";
    public static final String USER_APP_STATUS_UPDATE_ERROR_DESC = "应用状态更新失败，请稍后再试";
    public static final String USER_APP_NOT_EXIST = "20001017";
    public static final String USER_APP_NOT_EXIST_DESC = "该应用不存在";

    public static final String USER_NOT_EXIST = "30001001";
    public static final String USER_NOT_EXIST_DESC = "手机号未注册或输入有误，请核实";
    public static final String PASSWORD_IS_ERROR = "30001002";
    public static final String PASSWORD_IS_ERROR_DESC = "手机号或密码输入有误，请核实";
    public static final String LOGIN_LIMIT = "30001003";
    public static final String LOGIN_LIMIT_DESC = "您密码输入错误超过5次，暂时无法继续登录，请稍后再次尝试";
    public static final String SEND_MESSAGE_ERROR = "30001004";
    public static final String SEND_MESSAGE_ERROR_DESC = "";
    public static final String VERIFICATION_CODE_ERROR = "30001005";
    public static final String VERIFICATION_CODE_ERROR_DESC = "验证码无效，请核实";
    public static final String RESET_PWD_ERROR = "30001006";
    public static final String RESET_PWD_ERROR_DESC = "重置密码失败，请稍后再试";
    public static final String VERIFICATION_CODE_FREQUENTLY_ERROR = "30001007";
    public static final String VERIFICATION_CODE_FREQUENTLY_ERROR_DESC = "验证码发送过于频繁，请稍后再试";
    public static final String USER_EXIST = "30001008";
    public static final String USER_EXIST_DESC = "该手机号已注册，请勿重复注册";


    public static final String ORDER_NOT_EXIST = "40001001";
    public static final String ORDER_NOT_EXIST_DESC = "该订单不存在，请核实订单号";
    public static final String GOODS_NOT_EXIST = "40001002";
    public static final String GOODS_NOT_EXIST_DESC = "商品不存在";
    public static final String ACCOUNT_NOT_ENOUGH = "40001003";
    public static final String ACCOUNT_NOT_ENOUGH_DESC = "账户余额不足";
    public static final String BOOK_IN_BOOK_STUDY = "40001005";
    public static final String BOOK_IN_BOOK_STUDY_DESC = "书房已存在图书";
    public static final String APP_NOT_HAS_AUTH = "40001006";
    public static final String APP_NOT_HAS_AUTH_DESC = "应用无此图书权限";
    public static final String SYSTEM_ERROR_CODE = "40001004";
    public static final String SYSTEM_ERROR_DESC = "系统错误";

    /************** 权限校验 参数校验(param)**********************/

    public static final String PARAM_NOT_BLANK_DESC = "不能为空";
    public static final String PARAM_APP_KEY_NOT_EXIST_DESC = "非法appKey信息";
    public static final String PARAM_TIMESTAMP_REGEX_DESC = "格式错误";
    public static final String PARAM_TIMESTAMP_INVALID_DESC = "已失效，请求无效";
    public static final String SIGN_BODY_CONTENT_ERROR_DESC = "加密错误";
    public static final String SIGN_ERROR_DESC = "签名错误";
    public static final String SIGN_ERROR_REQUEST_REPEAT_DESC = "请勿重复提交";


    public static void main(String[] args) {
        BigDecimal goodsPrice = new BigDecimal("0.001");
        int result = goodsPrice.compareTo(new BigDecimal("0"));
        System.out.println(result);
        goodsPrice = new BigDecimal("0.000");
        result = goodsPrice.compareTo(new BigDecimal("0"));
        System.out.println(result);
    }
}
