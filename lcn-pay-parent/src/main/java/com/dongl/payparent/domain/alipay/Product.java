package com.dongl.payparent.domain.alipay;

import java.io.Serializable;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName Product.java
 * @Description TODO
 * @createTime 2021-09-16 10:55:00
 */
public class Product implements Serializable {

    private String id;

    private String name;

    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }
}
