package com.dongl.easyexcel.utils.excel;


import com.dongl.easyexcel.domian.Customer;
import com.dongl.easyexcel.domian.UploadData;
import com.dongl.easyexcel.mapper.CustomerMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author D-L
 * @program: easy-excel
 * @description:  存放数据库 存储方法 处理数据类型转换
 * @date 2021-09-14 13:50:54
 */
@Component
public class UploadMapperUtils {

    @Resource
    private CustomerMapper customerMapper;

    /**
     *
     * @param list
     */
    public void saveExcelList(List<UploadData> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        System.out.println("调用插入数据库方法-------------------------------------------------------002");
        System.out.println(list.size());
        Customer customer = new Customer();
        customer.setUid("aaa");
        customer.setPassword("bbb");
        customerMapper.insertSelective(customer);
    }


    public void saveExcelList01(List<Customer> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        System.out.println("调用插入数据库方法-------------------------------------------------------002");
        System.out.println(list.size());
        list.forEach(customer ->{
            System.out.println(customer);
        });

    }
}
