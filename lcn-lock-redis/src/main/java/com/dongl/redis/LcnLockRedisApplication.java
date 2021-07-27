package com.dongl.redis;

import com.dongl.common.distributedid.IDWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class LcnLockRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnLockRedisApplication.class, args);
    }

    @Bean
    public IDWorker getBean(){
        return new IDWorker(1,2);
    }

    @Bean
    public ThreadPoolTaskExecutor getThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int cpuNum = Runtime.getRuntime().availableProcessors();
        int threadNum = cpuNum * 2 + 1;
        System.out.println(threadNum);
        executor.setCorePoolSize(threadNum-1);
        executor.setMaxPoolSize(threadNum);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("service-user_");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
