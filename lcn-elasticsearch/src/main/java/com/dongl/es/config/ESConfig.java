package com.dongl.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ESConfig.java
 * @Description TODO
 * @createTime 2021-07-15 14:36:00
 */
@Configuration
public class ESConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        //有几个集群写几个！！
                        new HttpHost("192.168.10.61", 9200, "http")
                )
        );
    }
}
