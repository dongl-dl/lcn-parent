package com.dongl.payparent.utils;

/**
 * @Classname InvalidSystemClock
 * @Description TODO
 * @Date 2019/3/21 20:47
 * @Created by 爆裂无球
 */
public class InvalidSystemClock extends RuntimeException {
    public InvalidSystemClock(String message) {
        super(message);
    }
}
