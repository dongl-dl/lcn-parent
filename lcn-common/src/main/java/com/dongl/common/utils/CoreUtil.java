package com.dongl.common.utils;

import com.dongl.common.enums.IdEnum;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;


public class CoreUtil {

    /**
     * 方案一：通用core生成 ，年月日时分秒毫秒
     *
     * @param coreFirstCharacter
     * @return
     */
    public static String createCommonsCore(String coreFirstCharacter) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer str = new StringBuffer(coreFirstCharacter);
        str.append(format.format(date));
        return str.toString();
    }


    /**
     * 方案二：通用core生成 ，年月日时分秒毫秒+随机数
     *
     * @param coreFirstCharacter
     * @return
     */
    public static String createCommonsCore(String coreFirstCharacter, int numOfRandom) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer str = new StringBuffer(coreFirstCharacter);
        str.append(format.format(date));
        String random = create(numOfRandom);
        str.append(random);
        return str.toString();
    }

    /**
     * 方案三：
     * @param ie
     * @return
     */
    public static String createCommonsCore(IdEnum ie) {
        Objects.requireNonNull(ie, "ID业务类型不能为空！");
        return createCommonsCore(ie.getPrefix(), ie.getRandomNum());
    }

    /**
     * 方案四：通用core生成 ，年月日+时分秒毫秒（32位）+随机数
     * @param ie
     * @return
     */
    public static String createCommonsCore32(IdEnum ie) {
        return createCommonsCore32(ie.getPrefix(), ie.getRandomNum(), 0);
    }

    /**
     * 通用core生成 ，年月日+时分秒毫秒（32位）+随机数
     *
     * @param coreFirstCharacter
     * @return
     */
    public static String createCommonsCore32(String coreFirstCharacter, int numOfRandom, int incr) {
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatTime = new SimpleDateFormat("HHmmssSSS");
        StringBuffer str = new StringBuffer(coreFirstCharacter);
        str.append(formatDate.format(date));
        str.append(Base32Util.encode32(Long.parseLong(formatTime.format(date)) + 100000000 + incr));
        String random = create(numOfRandom);
        str.append(random);
        return str.toString();
    }

    /**
     * 生成n位随机数
     *
     * @param numOfRandom
     * @return
     */
    public static String create(int numOfRandom) {
        StringBuffer str = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < numOfRandom; i++) {
            str.append(random.nextInt(10) + "");
        }
        return str.toString();
    }


    public static void main(String[] args) throws Exception {

        System.out.println(createCommonsCore(IdEnum.UID));

        System.out.println(createCommonsCore("H"));

        System.out.println(createCommonsCore("U",4));

        String privilegeCode = createCommonsCore32(IdEnum.UID);
        System.out.println(privilegeCode);
    }

}
