package com.dongl.easyexcel.domian;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * 图片导出类
 */
@Data
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class TextImageData {


    private File file;

    private String name;

    private String url;
}
