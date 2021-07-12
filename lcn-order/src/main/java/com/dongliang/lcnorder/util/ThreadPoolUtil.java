package com.dongliang.lcnorder.util;

import java.util.concurrent.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ThreadPoolUtil.java
 * @Description TODO
 * @createTime 2021-05-20 14:19:00
 */
public class ThreadPoolUtil {
    /** 工具类，构造方法私有化 */
    private ThreadPoolUtil() {super();};

    // 线程池核心线程数
    private final static Integer COREPOOLSIZE = 4;
    // 最大线程数
    private final static Integer MAXIMUMPOOLSIZE = 10;
    // 空闲线程存活时间
    private final static Integer KEEPALIVETIME = 3 * 60;
    // 线程等待队列
    private static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);
    // 线程池对象
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(COREPOOLSIZE, MAXIMUMPOOLSIZE,
            KEEPALIVETIME, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 向线程池提交一个任务,返回线程结果
     * @param r
     * @return
     */
    public static<T> Future<T> submit(Callable<T> r) {
        return threadPool.submit(r);
    }

    /**
     * 向线程池提交一个任务，不关心处理结果
     * @param r
     */
    public static void execute(Runnable r) {
        threadPool.execute(r);
    }

    /** 获取当前线程池线程数量 */
    public static int getSize() {
        return threadPool.getPoolSize();
    }

    /** 获取当前活动的线程数量 */
    public static int getActiveCount() {
        return threadPool.getActiveCount();
    }
}
