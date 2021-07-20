package com.dongl.handlingexception.entity;


import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author D-L
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2021-07-20 17:51:00
 */
@Data
public class User {
    /**主键*/
    private Long id;
    /**姓名*/
    @NotNull
    private String name;
    /**性别*/
    private String sex;
    /**出生日期*/
    private Date birthDay;
    /**年龄*/
    @Range(min = 20 , max = 99)
    private Short age;
    /**详细地址*/
    private String address;
}
