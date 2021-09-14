package com.dongl.easyexcel.domian;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author D-L
 * @program: easy-excel
 * @description:  基础数据类
 * @date 2021-09-14 11:46:18
 */
@Data
public class DownloadData {
    @ExcelProperty("标题001")
    private String string;
    @ExcelProperty("日期标题001")
    private Date date;
    @ExcelProperty("数字标题001")
    private Double doubleData;
}
