package com.dongl.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class LcnElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnElasticsearchApplication.class, args);
    }


    @GetMapping("/es")
    public String getEs(){
        return "es-----------";
    }

}
