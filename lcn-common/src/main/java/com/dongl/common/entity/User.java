package com.dongl.common.entity;

import lombok.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2021-07-20 09:28:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Integer id;
    private String name;
}
