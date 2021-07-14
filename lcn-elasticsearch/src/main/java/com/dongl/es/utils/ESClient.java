package com.dongl.es.utils;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;

import java.io.IOException;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ESClient.java
 * @Description TODO
 * @createTime 2021-07-14 17:11:00
 */
public class ESClient {

    private static ESClient esClient;
    private String host = "localhost:9200,localhost:9201";
    private RestClientBuilder builder;
    private static Sniffer sniffer;
    private static RestHighLevelClient highClient;

    private ESClient(){}

    public static ESClient getInstance(){
        if (esClient == null) {
            synchronized (ESClient.class){
                if (esClient == null) {
                    esClient = new ESClient();
                    esClient.initBuilder();
                }
            }
        }
        return esClient;
    }

    private RestClientBuilder initBuilder() {
        String[] hosts = host.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < httpHosts.length; i++) {
            String[] host = hosts[i].split(":");
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), "http");
        }
        builder = RestClient.builder(httpHosts);

        //region 在Builder中设置请求头
        //  1.设置请求头
        Header[] defaultHeaders = new Header[]{
                new BasicHeader("Content-Type", "application/json")
        };
        builder.setDefaultHeaders(defaultHeaders);
        return builder;
    }

    public RestHighLevelClient getHighLevelClient() {
        if (highClient == null) {
            synchronized (ESClient.class) {
                if (highClient == null) {
                    highClient = new RestHighLevelClient(builder);
//                    //十秒刷新并更新一次节点
                    sniffer = Sniffer.builder(highClient.getLowLevelClient())
                            .setSniffIntervalMillis(5000)
                            .setSniffAfterFailureDelayMillis(15000)
                            .build();
                }
            }
        }
        return highClient;
    }
    /**
     *
     * 关闭sniffer client
     */
    public void closeClient() {
        if (null != highClient) {
            try {
                sniffer.close();    //需要在highClient close之前操作
                highClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
