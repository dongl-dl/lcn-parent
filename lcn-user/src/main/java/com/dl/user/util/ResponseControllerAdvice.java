package com.dl.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ResponseControllerAdvice.java
 * @Description 处理全局异常
 * @createTime 2021-06-16 09:37:00
 */

@RestControllerAdvice(basePackages = {"com.dongliang.lcnorder.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是 ResponseParams 那就没有必要进行额外的操作，返回false
        return !methodParameter.getGenericParameterType().equals(ResponseParams.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (methodParameter.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在 ResponseParams 里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(new ResponseParams<>(data));
            } catch (JsonProcessingException e) {
                // CustomizeException 为自定义异常
                throw new CustomizeException("返回String类型错误");
            }
        }
        // 将原本的数据包装在 ResponseParams 里
        return new ResponseParams<>(data);
    }
}