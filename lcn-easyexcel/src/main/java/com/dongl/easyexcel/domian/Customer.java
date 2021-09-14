package com.dongl.easyexcel.domian;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Customer {

    private String uid;

    private String customerName;

    private String password;

    private String mobile;

    private Date birthday;

    private Integer age;
}