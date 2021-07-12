package com.dongl.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName testCreateJwt.java
 * @Description TODO
 * @createTime 2021-06-29 16:35:00
 */
@SpringBootTest
public class testCreateJwt {

    /****
     * 创建Jwt令牌
     */
    @Test
    public void testCreateJwt(){
        JwtBuilder builder= Jwts.builder()
                .setId("888")             //设置唯一编号
                .setSubject("小白")       //设置主题  可以是JSON数据
                .setIssuedAt(new Date())  //设置签发日期
                .setExpiration(new Date())//用于设置过期时间 ，参数为Date类型数据
                .signWith(SignatureAlgorithm.HS256,"itcast");//设置签名 使用HS256算法，并设置SecretKey(字符串)

        //自定义数据
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("name" , "董亮");
        userInfo.put("age" , 18);
        builder.addClaims(userInfo);


        //构建 并返回一个字符串
        System.out.println( builder.compact() );
    }

    /***
     * 解析Jwt令牌数据
     */
    @Test
    public void testParseJwt(){
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjIyODd9.RBLpZ79USMplQyfJCZFD2muHV_KLks7M1ZsjTu6Aez4";
        Claims claims = Jwts.parser().
                setSigningKey("itcast").
                parseClaimsJws(compactJwt).
                getBody();

        System.out.println(claims);
    }

}
