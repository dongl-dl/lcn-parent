package com.dongliang.lcnorder.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName CommonThreadPoolUtil.java
 * @Description TODO
 * @createTime 2021-05-21 15:03:00
 */
public class CommonThreadPoolUtil {
    private static final String THREAD_POOL_FILE = "properties/threadpool.properties";
    private static final String DEFAULT_PREFIX = "default";
    private static Map<String, ExecutorService> threadMap = new ConcurrentHashMap<String, ExecutorService>();
    private static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    /**
     * 创建线程池,参数线程配置前缀
     * @param preFix
     * @return
     * @throws Exception
     */
    public static  ExecutorService createThreadPool(String preFix) throws Exception {

        preFix = preFix == null ? DEFAULT_PREFIX : preFix;
        Properties config = new Properties();
        config.load(CommonThreadPoolUtil.class.getClassLoader().getResourceAsStream(THREAD_POOL_FILE));
        ThreadModel threadModel = new ThreadModel();
        BeanInfo beanInfo = Introspector.getBeanInfo(threadModel.getClass());
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        for(String key : config.stringPropertyNames()) {
            if (key.startsWith(preFix)){
                String fieldName = key.substring(preFix.length()+1);
                for (PropertyDescriptor property : properties) {
                    if (property.getName().equals(fieldName)) {
                        property.getWriteMethod().invoke(threadModel,Integer.parseInt(config.getProperty(key)));
                    }
                }
            }
        }
        return createExecutorService(threadModel);
    }

    public static ExecutorService getExecuteThreadTask(String preFix) {
        preFix = preFix == null ? DEFAULT_PREFIX : preFix;
        return threadMap.get(preFix);
    }

    public static ExecutorService executeThreadTask(Runnable runnable) throws Exception {
        ExecutorService executorService = threadMap.get(DEFAULT_PREFIX);
        if (executorService == null) {
            executorService = createThreadPool(null);
            threadMap.put(DEFAULT_PREFIX, executorService);
        }
        executorService.execute(runnable);
        return executorService;
    }

    /**
     *
     * @param runnable
     * @param prefix
     * @return
     * @throws Exception
     */
    public static ExecutorService executeThreadTask(Runnable runnable,String prefix) throws Exception {

        ExecutorService executorService = threadMap.get(prefix);
        if (executorService == null) {
            executorService = createThreadPool(prefix);
            threadMap.put(prefix,executorService);
        }
        executorService.execute(runnable);
        return executorService;
    }

    private static ExecutorService createExecutorService(ThreadModel threadModel) {
        ExecutorService executorService = new ThreadPoolExecutor(threadModel.getCorePoolSize(),
                threadModel.getMaximumPoolSize(), threadModel.getKeepAliveTime(), TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(threadModel.getWorkQueueSize()),
                HANDLER);
        return executorService;
    }

    /**
     *
     * @param callable
     * @param prefix
     * @return
     * @throws Exception
     */
    public static Future executeThreadTask(Callable callable, String prefix) throws Exception {

        ExecutorService executorService = threadMap.get(prefix);
        if (executorService == null) {
            executorService = createThreadPool(prefix);
            threadMap.put(prefix,executorService);
        }
        Future future = executorService.submit(callable);
        return future;
    }


    /**
     * 线程池模板类
     * @param <T>
     */
    public static class ThreadModel<T> {
        // 核心线程数
        private int corePoolSize = 4;
        // 最大线程数
        private int maximumPoolSize = 10;
        // 闲置线程存活时间
        private long keepAliveTime = 3*60;
        //队列大小
        private Integer workQueueSize = Integer.MAX_VALUE;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public void setMaximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
        }

        public long getKeepAliveTime() {
            return keepAliveTime;
        }

        public void setKeepAliveTime(long keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
        }

        public Integer getWorkQueueSize() {
            return workQueueSize;
        }

        public void setWorkQueueSize(Integer workQueueSize) {
            this.workQueueSize = workQueueSize;
        }
    }
}
