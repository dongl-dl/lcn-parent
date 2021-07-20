package com.dongl.common.entity;

import lombok.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserInfo.java
 * @Description TODO
 * @createTime 2021-07-20 09:39:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo {
    private Integer id;
    private String name;
}
