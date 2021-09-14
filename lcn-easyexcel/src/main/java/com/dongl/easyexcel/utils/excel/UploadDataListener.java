package com.dongl.easyexcel.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author D-L
 * @program: easy-excel
 * @description:  模板的读取类
 * @date 2021-09-14 11:46:18
 */
@Slf4j
public class UploadDataListener <T,R> extends AnalysisEventListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    List<T> list = new ArrayList<T>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private R uploadMapperUtils;
    private String method;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param uploadMapperUtils
     * @param method
     */
    public UploadDataListener(R uploadMapperUtils ,String method) {
        this.uploadMapperUtils = uploadMapperUtils;
        this.method = method;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data 将excel解析出来的数据一条一条的放到data中
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 通过Java反射找到对应的入库方法 将list数据插入数据库
     */
    private void saveData(){
        log.info("{}条数据，开始存储数据库！", list.size());
        try {
            Method saveExcelList = uploadMapperUtils.getClass().getMethod(method, List.class);
            saveExcelList.invoke(uploadMapperUtils , list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("存储数据库成功！");
    }
}
