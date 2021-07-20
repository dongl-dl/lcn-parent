package com.dongl.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LcnRocketmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnRocketmqApplication.class, args);
    }

}
