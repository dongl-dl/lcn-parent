package com.dongl.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName CommonIo.java
 * @Description TODO
 * @createTime 2021-07-20 09:13:00
 */
public class CommonIoUtil {

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\demo1.xlsx");
        // 读取文件
        List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
        // 写入文件
        FileUtils.writeLines(new File("demo2.txt"), lines);
        // 复制文件
//        FileUtils.copyFile(srcFile, destFile);
    }
}
