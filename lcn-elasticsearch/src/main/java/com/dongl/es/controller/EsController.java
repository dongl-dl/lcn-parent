package com.dongl.es.controller;

import com.dongl.es.domain.User;
import com.dongl.es.utils.ESClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName EsController.java
 * @Description TODO
 * @createTime 2021-07-14 17:01:00
 */
@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private ESClient esClient;

    @GetMapping("/insertData")
    public String insertData(){
        User user = User.builder().id(1).age(18).mobile("15566545532").nickName("董亮").pwd("123456").balance(1000.00).regtime(new Date()).build();
        try {
            esClient.insertData(UUID.randomUUID().toString(), String.valueOf(12),user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }


    @GetMapping("/queryPage")
    public Object queryPage() throws IOException {
        SearchHits searchHits = esClient.queryPage("c9fc0470-5de7-477a-8fa7-d9dfa9c4b009", -1, 10);
        SearchHit[] hits = searchHits.getHits();
        return searchHits;
    }

    @GetMapping("/queryLikeQuery")
    public Object queryLikeQuery() throws IOException {
        SearchHit[] searchHits = esClient.queryLikeQuery("c9fc0470-5de7-477a-8fa7-d9dfa9c4b009", -1, 10, "155", null, null);
        return searchHits;
    }
}
