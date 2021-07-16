package com.dl.user.controller;

import com.dl.user.service.AsyncService;
import com.dongl.common.distributedid.IDWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName AsyncController.java
 * @Description TODO
 * @createTime 2021-07-15 16:52:00
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    @Autowired
    private AsyncService asyncService;

    @Autowired
    private IDWorker idWorker;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @GetMapping("/async")
    public void async(){
        asyncService.executeAsync();
    }

    @GetMapping("/async1")
    public String async1(){
        Future<String> submit = taskExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {

                String data = "success";
                System.out.println(idWorker.nextId());
                return data;
            }
        });

        String data = null;
        try {
            data = submit.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
