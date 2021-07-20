package com.dongl.common.utils;

import com.dongl.common.entity.User;
import com.dongl.common.entity.UserInfo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.text.ParseException;
import java.util.Date;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName CommonLang3Util.java
 * @Description TODO
 * @createTime 2021-07-20 10:04:00
 */
public class CommonLang3Util {

    public static void main(String[] args) throws ParseException {
   /*     String str = "yideng";
        String capitalize = StringUtils.capitalize(str);
        System.out.println(capitalize); // 输出Yideng

*/
      /*  String str = StringUtils.repeat("ab", 3);
        System.out.println(str); // 输出ababab*/


// Date类型转String类型
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(date); // 输出 2021-05-01 01:01:01

// String类型转Date类型
        Date date0 = DateUtils.parseDate("2021-07-20 10:10:05", "yyyy-MM-dd HH:mm:ss");
        System.out.println(DateFormatUtils.format(date0, "yyyy-MM-dd HH:mm:ss"));

// 计算一个小时后的日期
        Date date1 = DateUtils.addHours(new Date(), 1);
        System.out.println(DateFormatUtils.format(date1, "yyyy-MM-dd HH:mm:ss"));

// 返回两个字段
        ImmutablePair<Integer, String> pair = ImmutablePair.of(1, "yideng");
        System.out.println(pair.getLeft() + "," + pair.getRight()); // 输出 1,yideng

// 返回三个字段
        ImmutableTriple<Integer, String, Date> triple = ImmutableTriple.of(1, "yideng", new Date());
        System.out.println(triple.getLeft() + "," + triple.getMiddle() + "," + triple.getRight()); // 输出 1,yideng,Wed Apr 07 23:30:00 CST 2021


        User user = new User();
        UserInfo userInfo = new UserInfo();

        ImmutableTriple<User, UserInfo, Date> newInfo = ImmutableTriple.of(user.builder().id(1).name("dongl").build(), userInfo.builder().id(1).name("lixinyi").build(), new Date());

        System.out.println(newInfo.getLeft() + "," + newInfo.getMiddle() + "," + newInfo.getRight());
    }
}
