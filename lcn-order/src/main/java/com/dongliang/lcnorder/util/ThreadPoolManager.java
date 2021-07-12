package com.dongliang.lcnorder.util;

import java.util.concurrent.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ThreadPoolManager.java
 * @Description 线程池工具类(单例)
 * @createTime 2021-05-21 22:12:00
 */
public class ThreadPoolManager {

    //volatile 可以保证每次读取对象是在主内存中读取的
    private volatile static ThreadPool instance;

    //私有化构造子,阻止外部直接实例化对象
    private ThreadPoolManager() {}

    /**
     * 获取单例的线程池对象  单例的双重校验
     * @return
     */
    public static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    // 获取处理器数量
                    int cpuNum = Runtime.getRuntime().availableProcessors();
                    // 根据cpu数量,计算出合理的线程并发数
                    int threadNum = cpuNum * 2 + 1;
                    //默认是双核的cpu 每个核心走一个线程 一个等待线程
                    instance = new ThreadPool(threadNum - 1, threadNum, 60);
                }
            }
        }
        return instance;
    }

    /**
     * 线程池内部类
     * @param <T>
     */
    public static class ThreadPool<T> {

        private ThreadPoolExecutor mExecutor;
        // 核心线程数
        private int corePoolSize;
        // 最大线程数
        private int maximumPoolSize;
        // 闲置线程存活时间
        private long keepAliveTime;

        /**
         * 线程池构造函数
         * @param corePoolSize
         * @param maximumPoolSize
         * @param keepAliveTime
         */
        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        /**
         * 判断线程池实例是否为空  为空创建实例
         */
        private void initializeTheThreadPool() {
            if (mExecutor == null) {
                mExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                        // 线程队列
                        new LinkedBlockingDeque<Runnable>(Integer.MAX_VALUE),
                        // 线程工厂
                        Executors.defaultThreadFactory(),
                        // 队列已满,而且当前线程数已经超过最大线程数时的异常处理策略  这里可以自定义拒绝策略
                        new ThreadPoolExecutor.AbortPolicy()
                );
            }
        }

        /**
         * 向线程池提交一个任务,返回线程结果
         * @param c
         * @return
         */
        public Future<T> submit(Callable<T> c) {
            if (c == null) throw new NullPointerException();
            initializeTheThreadPool();
            return mExecutor.submit(c);
        }

        /**
         * 向线程池提交一个任务，不关心处理结果
         * @param r
         */
        public void execute(Runnable r) {
            if (r == null) throw new NullPointerException();
            initializeTheThreadPool();
            mExecutor.execute(r);
        }

        /** 获取当前线程池线程数量 */
        public int getSize() {
            return mExecutor.getPoolSize();
        }

        /** 获取当前活动的线程数量 */
        public int getActiveCount() {
            return mExecutor.getActiveCount();
        }

        /**从线程队列中移除对象*/
        public void cancel(Runnable runnable) {
            if (mExecutor != null) {
                mExecutor.getQueue().remove(runnable);
            }
        }
    }
}
