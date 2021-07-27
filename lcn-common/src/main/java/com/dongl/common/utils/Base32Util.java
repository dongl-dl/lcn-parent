package com.dongl.common.utils;


public class Base32Util {

    final static char[] digits32 = {
            '1', '2', '3', '4', '5', '6',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 把10进制的数字转换成32进制
     *
     * @param number
     * @return
     */
    public static String encode32(long number) {
        char[] buf = new char[32];
        int charPos = 32;
        int radix = 1 << 5;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits32[(int) (number & mask)];
            number >>>= 5;
        } while (number != 0);
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 把32进制的字符串转换成10进制
     *
     * @param decodeStr
     * @return
     */
    public static long decode32(String decodeStr) {
        long result = 0;
        for (int i = decodeStr.length() - 1; i >= 0; i--) {
            for (int j = 0; j < digits32.length; j++) {
                if (decodeStr.charAt(i) == digits32[j]) {
                    result += ((long) j) << 5 * (decodeStr.length() - 1 - i);
                }
            }
        }
        return result;
    }

}
