package com.dongl.easyexcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LcnEasyexcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnEasyexcelApplication.class, args);
    }

}
