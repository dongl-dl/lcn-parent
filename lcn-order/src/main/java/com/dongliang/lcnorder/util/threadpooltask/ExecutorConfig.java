package com.dongliang.lcnorder.util.threadpooltask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ExecutorConfig.java
 * @Description  ThreadPoolTaskExecutor 和 ThreadPoolExecutor
 * 分析下继承关系：
 *  1、ThreadPoolTaskExecutor extends (2)ExecutorConfigurationSupport
 * 	implements (3)AsyncListenableTaskExecutor, (4)SchedulingTaskExecutor
 * 2、 ExecutorConfigurationSupport extends CustomizableThreadFactory implements BeanNameAware, InitializingBean, DisposableBean
 * 3、public interface AsyncListenableTaskExecutor extends AsyncTaskExecutor
 * 4、public interface SchedulingTaskExecutor extends AsyncTaskExecutor
 * 从上继承关系可知：
 * ThreadPoolExecutor是一个java类不提供spring生命周期和参数装配。
 * ThreadPoolTaskExecutor实现了InitializingBean, DisposableBean ，xxaware等，具有spring特性
 * AsyncListenableTaskExecutor提供了监听任务方法(相当于添加一个任务监听，提交任务完成都会回调该方法)
 * 简单理解：
 * 1、ThreadPoolTaskExecutor使用ThreadPoolExecutor并增强，扩展了更多特性
 * 2、ThreadPoolTaskExecutor只关注自己增强的部分，任务执行还是ThreadPoolExecutor处理。
 * 3、前者spring自己用着爽，后者离开spring我们用ThreadPoolExecutor爽。
 * 注意：ThreadPoolTaskExecutor 不会自动创建ThreadPoolExecutor需要手动调initialize才会创建
 *     如果@Bean 就不需手动，会自动InitializingBean的afterPropertiesSet来调initialize
 *
 *
 * @createTime 2021-05-17 13:42:00
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.name.prefix}")
    private String namePrefix;

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
