package com.dongliang.lcnorder.test;

import cn.hutool.core.lang.Validator;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName HutoolTest.java
 * @Description TODO
 * @createTime 2021-05-24 10:54:00
 */
public class HutoolTest {
    public static void main(String[] args) {
        // 5***************1X
//        String idCardNum = DesensitizedUtil.idCardNum("51343620000320711X", 1, 2);

        // 180****1999
//        String mobilePhone = DesensitizedUtil.mobilePhone("18049531999");

        // **********
//        String password = DesensitizedUtil.password("1234567890");

//        System.out.println(idCardNum);
//        System.out.println(mobilePhone);
//        System.out.println(password);

//        User user = Singleton.get(User.class);
//        user.doPass();

        boolean email = Validator.isEmail("dl978916237@163.com");
        System.out.println(email);
        boolean between = Validator.isBetween(2, 3, 9);
        System.out.println(between);
        boolean birthday = Validator.isBirthday("2021-05-20");
        System.out.println(birthday);
        boolean carDrivingLicence = Validator.isCarDrivingLicence("浙A5656565656");
        System.out.println(carDrivingLicence);
        boolean chinese = Validator.isChinese("中文");
        System.out.println(chinese);

    }
}
