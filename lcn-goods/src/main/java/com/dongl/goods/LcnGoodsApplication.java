package com.dongl.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.dongl.goods.client"})
public class LcnGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnGoodsApplication.class, args);
    }

}
