package com.dongliang.lcnorder.util;

import java.util.concurrent.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ThreadPoolUtil_02.java
 * @Description TODO
 * @createTime 2021-05-20 14:33:00
 */
public class ThreadPoolUtil_02 {

    public static ThreadPoolExecutor threadPool;

    /**
     * 无返回值直接执行
     * @param runnable
     */
    public  static void execute(Runnable runnable){
        getThreadPool().execute(runnable);
    }

    /**
     * 返回值直接执行
     * @param callable
     */
    public  static <T> Future<T> submit(Callable<T> callable){
        return   getThreadPool().submit(callable);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        getThreadPool().shutdown();
    }

    /**
     * dcs获取线程池
     * @return 线程池对象
     */
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(10000), new ThreadPoolExecutor.CallerRunsPolicy());
                }
                return threadPool;
            }
        }
    }

}
