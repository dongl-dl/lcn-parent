package com.dongl.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName PasswordVerificationField.java
 * @Description 密码校验注解处理类
 * @createTime 2021-08-06 17:43:00
 */
public class PasswordVerificationField implements ConstraintValidator<PasswordVerification, Object> {

    /**
     * 从注解中获取合法的参数值
     */
    private String regularExpression;

    @Override
    public void initialize(PasswordVerification passwordVerification) {
        //初始化时获取注解上的值
        regularExpression = passwordVerification.regularExpression();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        //密码规则为8-20个字符，需同时包含数字、字母以及特殊符号（!@#$%^&*()等非空格符号）中至少2种组合
        //密码输入框内默认文本为“8-20个字符，需同时包含数字、字母以及特殊符号中至少2种组合”，点击输入框后默认文本消失，最多输入20位
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(o.toString());
        return m.matches();
    }
}
