package com.dongl.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName Hollections4Util.java
 * @Description 集合运算
 * @createTime 2021-07-20 09:46:00
 */
public class Collections4Util {
    public static void main(String[] args) {

        List<Integer> listA = Lists.newArrayList(1, 2, 3 ,4,5,6,7,8);
        List<Integer> listB = Lists.newArrayList(6, 7, 8,9,10,11,12,13);

         // 两个集合取交集
        Collection<Integer> collection = CollectionUtils.retainAll(listA, listB);
         // 两个集合取并集
        Collection<Integer> collection1 = CollectionUtils.union(listA, listB);
         // 两个集合取差集
        Collection<Integer> collection2 = CollectionUtils.subtract(listA, listB);

        Collection<Integer> collection3 = CollectionUtils.subtract(listB, listA);


        collection.forEach( coll ->{
            System.out.print(coll+",");
        });

        System.out.println("");
        collection1.forEach( coll ->{
            System.out.print(coll+",");
        });
        System.out.println("");
        collection2.forEach( coll ->{
            System.out.print(coll +",");
        });

        System.out.println("");
        collection3.forEach( coll ->{
            System.out.print(coll +",");
        });
    }
}
