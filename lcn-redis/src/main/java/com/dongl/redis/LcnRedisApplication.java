package com.dongl.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LcnRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnRedisApplication.class, args);
    }

}
