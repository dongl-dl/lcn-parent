package com.dongl.common.utils;

import com.google.common.collect.*;

import java.util.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName GoogleGuavaUtils.java
 * @Description TODO
 * @createTime 2021-07-19 17:46:00
 */
public class GoogleGuavaUtil {

    public static void main(String[] args) {
        //1、创建集合
/*//        List<String> list = Lists.newArrayList();
        List<Integer> list = Lists.newArrayList(1, 2, 3);
        // 反转list
        List<Integer> reverse = Lists.reverse(list);
        System.out.println(reverse); // 输出 [3, 2, 1]
        // list集合元素太多，可以分成若干个集合，每个集合10个元素
        List<List<Integer>> partition = Lists.partition(list, 10);

        Map<String, String> map = Maps.newHashMap();
        Set<String> set = Sets.newHashSet();*/


        //2、Multimap 一个key可以映射多个value的HashMap

       /* Multimap<String, Integer> map = ArrayListMultimap.create();
        map.put("key", 1);
        map.put("key", 2);
        Collection<Integer> values = map.get("key");
        System.out.println(map); // 输出 {"key":[1,2]}
        // 还能返回你以前使用的臃肿的Map
        Map<String, Collection<Integer>> collectionMap = map.asMap();*/



     /*   //3、BiMap 一种连value也不能重复的HashMap
        BiMap<String, String> biMap = HashBiMap.create();
        // 如果value重复，put方法会抛异常，除非用forcePut方法
        biMap.put("key","value");
        System.out.println(biMap); // 输出 {"key":"value"}
        // 既然value不能重复，何不实现个翻转key/value的方法，已经有了
        BiMap<String, String> inverse = biMap.inverse();
        System.out.println(inverse); // 输出 {"value":"key"}*/


        //4、Table 一种有两个key的HashMap

    /*    // 一批用户，同时按年龄和性别分组
        Table<Integer, String, String> table = HashBasedTable.create();
        table.put(18, "男", "yideng");
        table.put(18, "女", "Lily");
        System.out.println(table.get(18, "男")); // 输出 yideng
        // 这其实是一个二维的Map，可以查看行数据
        Map<String, String> row = table.row(18);
        System.out.println(row); // 输出 {"男":"yideng","女":"Lily"}
        // 查看列数据
        Map<Integer, String> column = table.column("男");
        System.out.println(column); // 输出 {18:"yideng"}*/


        //5、Multiset 一种用来计数的Set
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("apple");
        multiset.add("apple");
        multiset.add("orange");
        System.out.println(multiset.count("apple")); // 输出 2
        // 查看去重的元素
        Set<String> set = multiset.elementSet();
        System.out.println(set); // 输出 ["orange","apple"]
        // 还能查看没有去重的元素
        Iterator<String> iterator = multiset.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        // 还能手动设置某个元素出现的次数
        multiset.setCount("apple", 5);
    }
}
