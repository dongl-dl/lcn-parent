package com.dongl.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName passwordVerification.java
 * @Description 校验密码
 * @createTime 2021-08-06 17:26:00
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordVerificationField.class)
public @interface PasswordVerification {

    String message() default "内容格式不正确";

    String regularExpression() default "";

    Class<? extends Payload>[] payload() default {};
}
