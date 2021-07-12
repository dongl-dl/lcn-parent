package com.dongliang.lcnpay;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class LcnPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnPayApplication.class, args);
    }

}
