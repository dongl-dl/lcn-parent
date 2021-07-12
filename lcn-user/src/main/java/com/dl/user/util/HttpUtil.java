package com.dl.user.util;


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dl.user.util.HttpMethod.POST;


/**
 * @author xixiaoyi
 * @date 2019-04-24 08:10
 * @since 1.0.0
 */
public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static volatile HttpUtil instance;
    /**
     * 连接超时时间
     */
    private static int CONNECT_TIMEOUT = 60;
    /**
     * 读文件超时时间
     */
    private static int READ_TIMEOUT = 60;
    /**
     * 写文件超时时间  因为文件上传所以这个时间应该放大  单位为秒  这里默认为2小时
     */
    private static int WRITE_TIMEOUT = 2 * 60 * 60;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 正常请求
     */
    private static OkHttpClient OkHttpClient;

    private HttpUtil() {
        init();
    }

    private static void init() {
        initNormalClient();
    }

    static {
        init();
    }

    /**
     * 初始化普通请求
     */
    private static void initNormalClient() {
        // 进行数据初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 设置读取超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                // 设置写的超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 设置连接超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient = builder.build();
    }

    /**
     * 单体实例类
     *
     * @return
     */
    public static HttpUtil getInstance() {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }


    /**
     * 请求头信息 组装
     *
     * @param headerMap
     * @return
     */
    public static Headers buildHeaders(Map<String, String> headerMap) {
        Headers.Builder headers = new Headers.Builder();
        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach((k, v) -> {
                headers.add(k, v);
            });
        }
        return headers.build();
    }

    /**
     * 请求参数 组装
     *
     * @param bodyMap
     * @return
     */
    public static RequestBody buildRequestBody(Map<String, String> bodyMap) {
        FormBody.Builder requestBody = new FormBody.Builder();
        if (bodyMap != null && !bodyMap.isEmpty()) {
            bodyMap.forEach((k, v) -> {
                requestBody.add(k, v);
            });
        }
        return requestBody.build();
    }


    public static Map<String, String> okHttpPostJson(String url, Map<String, String> headerMap, String json) {
        Map<String, String> resultMap = new HashMap<>();
        Headers headers = buildHeaders(headerMap);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .method(POST, requestBody)
                .headers(headers)
                .url(url)
                .build();
        try {
            Response response = OkHttpClient.newCall(request).execute();
            if (response.body() != null) {
                resultMap.put("body", response.body().string());
            }
            resultMap.put("code", String.valueOf(response.code()));
            return resultMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static Map<String, String> okHttpPostJson(String url, String json) {
        Map<String, String> resultMap = new HashMap<>();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .method(POST, requestBody)
                .url(url)
                .build();
        try {
            Response response = OkHttpClient.newCall(request).execute();
            if (response.body() != null) {
                resultMap.put("body", response.body().string());
            }
            resultMap.put("code", String.valueOf(response.code()));
            return resultMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
