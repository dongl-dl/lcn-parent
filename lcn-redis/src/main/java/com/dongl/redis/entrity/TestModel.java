package com.dongl.redis.entrity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName TestModel.java
 * @Description TODO
 * @createTime 2021-07-29 17:38:00
 */
@Data
public class TestModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
}
