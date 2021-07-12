package com.dongliang.lcnorder.util.pagehelper;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName PageUtils.java
 * @Description 分页结果处理工具类
 * @createTime 2021-06-15 16:15:00
 */
public class PageUtils {
    public static <T extends List> PageResult<T> build(T list){
        PageResult<T> result = new PageResult<T>();
        if(list == null){
            return new PageResult<T>();
        }
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setTotal(page.getTotal());
            result.setTotalPage(page.getPages());
            result.setList(list);
        }
        return result;
    }
}
