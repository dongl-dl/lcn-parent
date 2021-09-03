package com.dongl.common.utils;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName IpLongUtils.java
 * @Description
 *
 * 相对字符串存储，使用无符号整数来存储有如下的好处：
 * 节省空间，不管是数据存储空间，还是索引存储空间
 * 便于使用范围查询（BETWEEN...AND），且效率更高
 *
 * 通常，在保存IPv4地址时，一个IPv4最小需要7个字符，最大需要15个字符，所以，使用VARCHAR(15)即可。MySQL在保存变长的字符串时，还需要额外的一个字节来保存此字符串的长度。而如果使用无符号整数来存储，只需要4个字节即可。
 * 另外还可以使用4个字段分别存储IPv4中的各部分，但是通常这不管是存储空间和查询效率应该都不是很高（可能有的场景适合使用这种方式存储）。
 *
 * @createTime 2021-08-20 09:17:00
 */
public class IpLongUtils {

    /**
     * 把字符串IP转换成long
     *
     * @param ipStr 字符串IP
     * @return IP对应的long值
     */
    public static long ip2Long(String ipStr) {
        String[] ip = ipStr.split("\\.");
        return (Long.valueOf(ip[0]) << 24) + (Long.valueOf(ip[1]) << 16)
                + (Long.valueOf(ip[2]) << 8) + Long.valueOf(ip[3]);
    }

    /**
     * 把IP的long值转换成字符串
     *
     * @param ipLong IP的long值
     * @return long值对应的字符串
     */
    public static String long2Ip(long ipLong) {
        StringBuilder ip = new StringBuilder();
        ip.append(ipLong >>> 24).append(".");
        ip.append((ipLong >>> 16) & 0xFF).append(".");
        ip.append((ipLong >>> 8) & 0xFF).append(".");
        ip.append(ipLong & 0xFF);
        return ip.toString();
    }

    public static void main(String[] args) {
        System.out.println(ip2Long("192.168.0.1"));
        System.out.println(long2Ip(3232235521L));
        System.out.println(ip2Long("10.0.0.1"));
    }
}
