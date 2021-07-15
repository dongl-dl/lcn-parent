package com.dongl.es.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 * @author 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    private String mobile;

    private String nickName;

    private Integer age;

    private String pwd;

    private Boolean type;

    private Double balance;

    private Date regtime;


    private static final long serialVersionUID = 1L;

    public User(String nickName) {
        this.nickName = nickName;
    }

    public User(String nickName, Integer age, String pwd) {
        this.nickName = nickName;
        this.age = age;
        this.pwd = pwd;
    }

    public void doPass() {
        System.out.println("do something ------------------------");
    }
}