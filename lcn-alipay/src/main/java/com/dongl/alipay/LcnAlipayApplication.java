package com.dongl.alipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LcnAlipayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnAlipayApplication.class, args);
    }

}
