package com.dongl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2021-06-23 09:27:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MqUser {

    private String name;

    private String sex;

    private Integer age;

    private String address;
}
