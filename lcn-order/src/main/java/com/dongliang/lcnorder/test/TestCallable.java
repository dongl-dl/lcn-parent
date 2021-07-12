package com.dongliang.lcnorder.test;

import com.dongliang.lcnorder.util.ThreadPoolUtil_02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName TestCallable.java
 * @Description TODO
 * @createTime 2021-05-20 14:35:00
 */
public class TestCallable implements Callable<String> {
    private String message;

    public TestCallable(String message) {
        this.message = message;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(300);
        System.out.println(String.format("打印消息%s", message));
        return "OK";
    }


//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        long start = System.currentTimeMillis();
//        List<Future> futureList = new ArrayList();
//        // 发送10次消息
//        for (int i = 0; i < 10; i++) {
//            try {
//                Future<String> messageFuture = ThreadPoolUtil_02.submit(new TestCallable(String.format("这是第{%s}条消息", i)));
//                futureList.add(messageFuture);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (Future<String> message : futureList) {
//            String messageData = message.get();
//        }
//        System.out.println(String.format("共计耗时{%s}毫秒", System.currentTimeMillis() - start));
//    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        long start = System.currentTimeMillis();
        List<Future> futureList = new ArrayList();
        // 发送10次消息
        for (int i = 0; i < 10000; i++) {
            try {
                String msg = String.format("这是第{%s}条消息", i);
                Future<String> messageFuture = ThreadPoolUtil_02.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        atomicInteger.incrementAndGet();
                        Thread.sleep(1000);
                        System.out.println(String.format("打印消息%s", msg));
                        return "OK";
                    }
                });
                futureList.add(messageFuture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Future<String> message : futureList) {
            String messageData = message.get();
        }
        System.out.println(String.format("共计耗时{%s}毫秒", System.currentTimeMillis() - start));
        System.out.println(atomicInteger);
        ThreadPoolUtil_02.shutdown();
    }
}