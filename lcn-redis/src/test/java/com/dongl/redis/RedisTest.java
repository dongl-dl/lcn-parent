package com.dongl.redis;

import com.dongl.redis.utils.RedisUtil;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedisTest.java
 * @Description TODO
 * @createTime 2021-07-29 15:27:00
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置缓存过期时间
     */
    @Test
    public void testExpire() throws Exception {
        redisUtil.set("aaaKey", "aaaValue");
        redisUtil.expire("aaaKey", 10);
        Object aaaValue = redisUtil.get("aaaKey");
        System.out.println(aaaValue.toString());

    }

    @Test
    public void testGetExpire() throws Exception {
        redisUtil.set("aaaKey", "aaaValue");
        redisUtil.expire("aaaKey", 100);
        // 设置了缓存就会及时的生效，所以缓存时间小于最初设置的时间
        long aaaKey = redisUtil.getExpire("aaaKey");
        System.out.println(aaaKey);
    }

    @Test
    public void testHasKey() throws Exception {
        redisUtil.set("aaaKey", "aaaValue");
        // 存在的
        boolean aaaKey = redisUtil.hasKey("aaaKey");
        // 不存在的
        boolean bbbKey = redisUtil.hasKey("bbbKey");
        System.out.println(aaaKey + "---" + bbbKey);
    }

    @Test
    public void testDel() throws Exception {
        redisUtil.set("aaaKey", "aaaValue");
        redisUtil.set("bbbKey", "bbbValue");
        redisUtil.set("cccKey", "cccValue");

        // 批量删除缓存
        redisUtil.del("aaaKey","bbbKey");

        boolean aaaKey = redisUtil.hasKey("aaaKey");
        boolean bbbKey = redisUtil.hasKey("bbbKey");
        boolean cccKey = redisUtil.hasKey("cccKey");
        System.out.println(aaaKey + "---" + bbbKey + "---" + cccKey);
    }

    /*********************************String***********************************/

    @Test
    public void testGet() throws Exception {
        redisUtil.set("aaaKey", "aaaValue");
        Object aaaKey = redisUtil.get("aaaKey");
        System.out.println(aaaKey);
    }

    @Test
    public void testSetStringObject() throws Exception {
        boolean aaaKey = redisUtil.set("aaaKey", "aaaValue");
        System.out.println(aaaKey);
    }

    @Test
    public void testSetStringObjectLong() throws Exception {
        boolean aaaKeyLong = redisUtil.set("aaaKeyLong", 100L);
        System.out.println(aaaKeyLong);
    }

    @Test
    public void testSetObject() {
        // 测试对象
        TestModel testModel = new TestModel();
        testModel.setId(System.currentTimeMillis());
        testModel.setName("测试");
        redisUtil.set("testModel", testModel);
        TestModel testModel2 = (TestModel) redisUtil.get("testModel");
        System.err.println(testModel2);
        System.err.println(testModel2.getName());
        System.err.println(testModel2.getId());
    }

    @Test
    @Ignore
    public void testIncr() throws Exception {
        String key = "testIncr";
        redisUtil.incr(key, 1);
        redisUtil.expire(key, 10); // 缓存失效10s
        Object value = redisUtil.get(key);
        System.out.println(value);
    }

    // 高并发下 递增 测试
    @Test
    @Ignore
    public void testIncr2() throws Exception {
        // 模拟发送短信的并发
        // 首先开启一个线程池，创建一个专门消费短信的线程
        // 一次性放入多个线程实例 ，实例 都是2秒请求一次 ，而10s内的只能允许一条。 也就是说我测试100个线程，只能10s一条
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 6, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        Thread[] threads = new Thread[100];

        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    TimeUnit.SECONDS.sleep(2);
                    String key = "testIncr2_17353620612";
                    long count = redisUtil.incr(key, 1);
                    if (count == 1L) {
                        redisUtil.expire(key, 10); // 缓存失效10s
                        System.err.println("短信发送成功===" + new Date());

                    } else {
                        System.err.println("访问次数快===" + new Date());
                    }
                }
            });

            threadPoolExecutor.submit(threads[i]);
        }

        while (threadPoolExecutor.getQueue().isEmpty()) {
            threadPoolExecutor.shutdown();
            System.err.println("所有线程执行完毕");
        }

        System.in.read();// 加入该代码，让主线程不挂掉

//        // 启动线程
//        for (int i = 0; i < 100; i++) {
//            threads[i].start();
//        }
    }

    long count = 0L;

    // 高并发下 错误的测试 递增 测试
    @Test
    @Ignore
    public void testIncr3() throws Exception {
        // 模拟发送短信的并发

        // 首先开启一个线程池，创建一个专门消费短信的线程
        // 一次性放入多个线程实例 ，实例 都是2秒请求一次 ，而10s内的只能允许一条。 也就是说我测试100个线程，只能10s一条
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 6, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    pool-3-thread-1count===1
//                   pool-3-thread-5count===1
                    String key = "testIncr2_17353620612";
                    count = count + 1;
                    System.err.println(Thread.currentThread().getName() + "count===" + count);
                    // 督导的count
                    // if (count == 1L) {
//                        count = count - 1;
//                        System.err.println("短信发送成功===" + new Date());
//                    } else {
//                        System.err.println("访问次数快===" + new Date());
//                    }
                }
            });
            threadPoolExecutor.submit(threads[i]);
        }

        while (threadPoolExecutor.getQueue().isEmpty()) {
            threadPoolExecutor.shutdown();
            System.err.println("所有线程执行完毕");
        }

//        System.in.read();// 加入该代码，让主线程不挂掉
    }

    @Test
    @Ignore
    public void testDecr() throws Exception {
        String key = "Decr_17353620612";
        redisUtil.decr(key, 1);
        redisUtil.expire(key, 10); // 缓存失效10s
        System.out.println(redisUtil.get(key));
    }

    /*********************************Map***********************************/
    @Test
    public void testHget() throws Exception {
        redisUtil.hset("testHget", "dongl", "456123");
        Object hget = redisUtil.hget("testHget", "dongl");
        System.out.println(hget);
    }

    @Test
    public void testHmget() throws Exception {
        redisUtil.hset("testHmget", "testHmget1", "testHmget1");
        redisUtil.hset("testHmget", "testHmget2", "testHmget2");
        Map<Object, Object> map = redisUtil.hmget("testHmget");
        if (MapUtils.isNotEmpty(map)) {
            for (Map.Entry<Object, Object> e : map.entrySet()) {
                System.err.println(e.getKey() + "===" + e.getValue());
            }
        }
    }

    @Test
    public void testHsetStringStringObject() throws Exception {
        redisUtil.hset("map", "testHsetStringStringObject", "testHsetStringStringObject");

    }

    // 测试放在hash 里面的对象
    @Test
    public void testHsetObject() {
        // 测试对象
        TestModel testModel = new TestModel();
        testModel.setId(System.currentTimeMillis());
        testModel.setName("测试");
        redisUtil.hset("hash", "testModel", testModel);
        TestModel testModel2 = (TestModel) redisUtil.hget("hash", "testModel");
        System.err.println(testModel2);
        System.err.println(testModel2.getName());
        System.err.println(testModel2.getId());
    }

    // 太奇妙了 放进去Long 取出来会根据大小变为相应的数据类型
    @Test
    public void testHsetStringStringObjectLong() throws Exception {
        redisUtil.hset("testHsetStringStringObjectLong", "int", 100L); // java.lang.Integer 读取来是inter
        String typeName = redisUtil.hget("testHsetStringStringObjectLong", "int").getClass().getTypeName();
        System.err.println(typeName);
        redisUtil.hset("testHsetStringStringObjectLong", "long", System.currentTimeMillis()); // java.lang.Integer 读取来是inter
        Object hget1 = redisUtil.hget("testHsetStringStringObjectLong", "long");
        String typeName1 = hget1.getClass().getTypeName();
        System.err.println(typeName1);

    }

    @Test
    public void testHdel() throws Exception {
        redisUtil.hset("testHdel", "int1", 211);
        redisUtil.hset("testHdel", "int2", 985);
        System.out.println(redisUtil.hget("testHdel", "int1"));
        redisUtil.hdel("testHdel", "int1");
        System.out.println(redisUtil.hget("testHdel", "int1"));
    }

    @Test
    public void testHHasKey() throws Exception {
        redisUtil.hset("testHHasKey", "int", 100);
        System.out.println(redisUtil.hHasKey("testHHasKey", "int"));

    }

    @Test
    public void testHincr() throws Exception {
        System.err.println(redisUtil.hincr("testHincr", "testHincr", 1));

    }

    @Test
    public void testHdecr() throws Exception {
        System.err.println(redisUtil.hincr("testHincr", "testHincr", 1));
        double hincr = redisUtil.hincr("testHincr", "testHincr", 1);
    }


    /*********************************Set***********************************/
    @Test
    public void testSGet() throws Exception {
        redisUtil.sSet("testSGet", "testSGet1");
        redisUtil.sSet("testSGet", "testSGet2");
        Set<Object> testSGet = redisUtil.sGet("testSGet");
        System.err.println(StringUtils.join(testSGet, ","));
    }

    @Test
    public void testSHasKey() throws Exception {
        redisUtil.sSet("testSHasKey", "value1");
        redisUtil.sSet("testSHasKey", "value2");
        redisUtil.sSet("testSHasKey", "value3");
        System.err.println(redisUtil.sHasKey("testSHasKey", "value2"));

    }

    @Test
    public void testSSet() throws Exception {
        redisUtil.sSet("testSSet", "a" , "b" , "c" , "d" , "e");
        System.out.println(redisUtil.sGet("testSSet"));
    }

    @Test
    public void testSSetAndTime() throws Exception {
        redisUtil.sSetAndTime("testSSetAndTime", 20, "testSSetAndTime1");
        System.err.println(StringUtils.join(redisUtil.sGet("testSSetAndTime"), ","));
        redisUtil.sSetAndTime("testSSetAndTime", 5, "testSSetAndTime2");
        System.err.println(StringUtils.join(redisUtil.sGet("testSSetAndTime"), ","));
        TimeUnit.SECONDS.sleep(5);
        System.err.println(StringUtils.join(redisUtil.sGet("testSSetAndTime"), ",-----"));
    }

    @Test
    public void testSGetSetSize() throws Exception {
        redisUtil.sSetAndTime("testSGetSetSize", 20, "testSGetSetSize1");
        redisUtil.sSetAndTime("testSGetSetSize", 5, "testSGetSetSize");
        System.err.println(redisUtil.sGetSetSize("testSGetSetSize"));
    }

    @Test
    public void testSetRemove() throws Exception {
        redisUtil.sSetAndTime("testSetRemove", 20, "testSetRemove1");
        redisUtil.sSetAndTime("testSetRemove", 5, "testSetRemove");
        System.out.println(redisUtil.sGetSetSize("testSetRemove"));
        redisUtil.setRemove("testSetRemove", "testSetRemove");
        System.out.println(redisUtil.sGetSetSize("testSetRemove"));

    }


    /*********************************List***********************************/
    @Test
    public void testLGet() throws Exception {
        redisUtil.lSet("testLGet", "testLGet0", 10); // 10秒过期
        redisUtil.lSet("testLGet", "testLGet1", 10);
        // 查询三个元素 2-0+1
        List<Object> list = redisUtil.lGet("testLGet", 0, 2);
        System.err.println("list===" + list);
        // 查询两个
        List<Object> list2 = redisUtil.lGet("testLGet", 0, 1);
        System.err.println("list2===" + list2);
        // 查询全部
        List<Object> list3 = redisUtil.lGet("testLGet", 0, -1);
        System.err.println("list3===" + list3);

    }

    @Test
    public void testLGetListSize() throws Exception {
        // 看看重复元素会怎么处理
        redisUtil.lSet("testLGetListSize", "testLGetListSize0", 10); // 10秒过期
        System.err.println(redisUtil.lGetListSize("testLGetListSize"));

        redisUtil.lSet("testLGetListSize", "testLGetListSize0", 10);
        System.err.println(redisUtil.lGetListSize("testLGetListSize"));
    }


    @Test
    public void testLGetIndex() throws Exception {
        redisUtil.lSet("testLGetIndex", "testLGetIndex0", 50); // 10秒过期
        redisUtil.lSet("testLGetIndex", "testLGetIndex1", 50);
        System.err.println(redisUtil.lGetIndex("testLGetIndex", 0));
    }

    @Test
    public void testLSetStringObject() throws Exception {
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject0");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject1");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject2");
        redisUtil.lSet("testLSetStringObject", "testLSetStringObject1");
    }

    @Test
    public void testLSetStringObjectLong() throws Exception {
        redisUtil.lSet("testLSetStringObjectLong", 100L);
        redisUtil.lSet("testLSetStringObjectLong", 200L);
        redisUtil.lSet("testLSetStringObjectLong", 300L);
    }

    @Test
    public void testLUpdateIndex() throws Exception {
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex0");
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex1");
        redisUtil.lSet("testLUpdateIndex", "testLUpdateIndex2");
        Object obj = redisUtil.lUpdateIndex("testLUpdateIndex", 0, "更新的");
        System.out.println(obj);
    }

    @Test
    public void testLRemove() throws Exception {
        redisUtil.lSet("testLRemove2", "testLRemove0");
        redisUtil.lSet("testLRemove2", "testLRemove1");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        redisUtil.lSet("testLRemove2", "testLRemove3");
        redisUtil.lSet("testLRemove2", "testLRemove4");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        redisUtil.lSet("testLRemove2", "testLRemove2");
        Object obj = redisUtil.lRemove("testLRemove2", 2, "testLRemove2");
        System.out.println(obj);
    }
}

@Data
class TestModel implements Serializable {
    /**
     * @Fields serialVersionUID : (用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
}
