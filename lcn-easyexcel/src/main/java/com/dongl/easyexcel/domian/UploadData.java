package com.dongl.easyexcel.domian;

import lombok.Data;

import java.util.Date;

/**
 * @author D-L
 * @program: easy-excel
 * @description:  基础数据类
 * @date 2021-09-14 11:27:00
 */
@Data
public class UploadData {
    private String string;
    private Date date;
    private Double doubleData;
}
