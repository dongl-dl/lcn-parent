package com.dongl.handlingexception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LcnHandlingexceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnHandlingexceptionApplication.class, args);
    }

}
