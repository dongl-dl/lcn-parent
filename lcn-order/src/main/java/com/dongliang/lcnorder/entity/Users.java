package com.dongliang.lcnorder.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * users
 * @author 
 */
@Data
public class Users implements Serializable {
    private Integer id;

    private String uname;

    private Integer userlevel;

    private Integer age;

    private String phonenum;

    private Date createtime;

    private Date lastupdate;

    private static final long serialVersionUID = 1L;
}