package com.dongliang.lcnorder;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.dongliang.lcnorder.config.initialize.InitMethodExampleBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDistributedTransaction
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.dongliang.lcnorder.api"})
public class LcnOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnOrderApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean(initMethod="init")
    public InitMethodExampleBean initMethodExampleBean() {
        return new InitMethodExampleBean();
    }
}
