package com.dongl.payparent.config;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName AlipayConfig.java
 * @Description 支付宝支付配置
 * @createTime 2021-09-16 10:46:00
 */
public class AlipayConfig {

/*    //这里用natapp内外网穿透
    public static final String natUrl = "http://gca8w8.natappfree.cc";

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092700605500";//在后台获取（必须配置）

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDI422iRmxcIzHkSrT6TBomi+ddPBR8apFBNyZovgnTPsrhGb6dJEfgjZJNaB0+tNFnHAiQ4ZPiy4CbsWFlGpgchUPE2eZJHOhJFJJm5FXI5UPQW9yuzxYyMVZFA5dobJAN18vjAU2Vkm1X2bdaJFKDfH61WAyuLzL9LbopYfRD87NlSkJmSbZfUukBqf0wOLqgBvzbJuojWMtwlgmhLi7g08jptTPtWKnBXoluWO32+YCFpM2DPEhswmormybxsC24IERqvfHX0zAMRJXYKRe8O7rEg2xZZhUJnBCFTrqGuZC0Ax9mOjLn8t6pmcSB2SMHBNJtg0SuRkwXrFfC5sg9AgMBAAECggEAMggCTAICBsh1Kog4noXJlYQOcTf/WiUoW/eiRz/dLpFa8cj5oiVhCVn47/JYJj/eTzG6KGsl67wHIb+BpC6FZ8UA5cvnAJqEd376H0oK22fOBdx7D8ZfnsyO0c1dLhKrpebyzmyKqA+5z8FXWsnlCv48iGmPbuouZsO2s4vDzBA6lLxA6AWmbZA29XTMBBzFpjoCmGY/fh2k6yfHdagt0VtG05ZpyHpMS2zau0el+2sJ4gcR9v0JbEMinSKMO8s5PAX2Rds+HwFC597oo9cVDBGvlwHc6uzJv/lQcRG5mdAercWQsGDzjlrzKPJKH7RTcu8jo5JOjmNCq+Xu4Ml2uQKBgQDxhdgKI2/AQLLWv6StUv4eTb5srpCG7GxpvlMPAZxTSie5ennnwq3byVOmZG1tEPhKFVhvpDYlkl6/u6gcGwITIB38J+fVd8koEczW4rNO0+9rFvxv026vnvhJpRvPHvxFtvC27XhjmIfU3agCmNy3mDX65FVHxleOKej/MmWLKwKBgQDU7g0HqqxQGO/9l1DrOBd7Pj6DJlT0QfqLR1idHz79aUu5YGf593jihQ7mTIqF5pWxEtYDPv8aZP+2GitL3E25HJXuHDql3DzRiTLCFX3tPuVFOJ/s+8Kvp4J89xBnSZk2ETz32+NH/xs2Z5K5kO1QL18I7jAOZhmzTtb4eIimNwKBgBuVmsgFphTCDurh9m/pQ6a4YGBPS6htRjCJkTjsiSsmFAVmDJhArtSkWM5vN+Q2BPWJHJGboqeF4RZq21diakk+6M3MzPifdrUB23BLcgk1wWlstaYb8s9SC691kQiF1HAt5a4uzwoo+JKCQlcRmFvLoZanD2pkJ8nP1ZGX8usDAoGAYOKgUyi5lwiByqWbUCwJoy5DS3GZnjqXQeCI1ZVuF+hdaFARf4+3FXrtuKSDM0IkQpoLfxafBOeH4tgtcysewmfbYKi/ltCLa9w9mEonLlg8Ai8ttasofrx9h1bzw8udRxi44R7QX+YX/71I6d6j3KRxXaLjgum6L/hjoFNX+ikCgYA1h9DECmRMcW5JK7BgczRMsKtwPXmiIXg01+VBILblaF3XqQvq48A7pmGG9uOcNaw3Bp5qQTdYFw76TVczo+xovqY+a9uFHk2/Kp6et1EKKYtwfgOfMkNG6jRCXD+bLVzDNEes4Fe96rBYhAe+yeP0K20h5UDKPr5eMmUCYqBMAA==";//教程查看获取方式（必须配置）

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.html 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8jVCAWpHgm7rZNtEmn5QoykMmrYBYhrZWyVaB8Ioxh31ijMYcKZ+ozY+L7LofzDSam0Q21yOHrbPukVqMw/ELBNdzz0LeNfdU4QryQsZVVrLg06FIP887F+yACxUTknuU4zA5eWLOVKxp5n2inwSJNnSkwsxSKDLmYCmHTilEIYwYIGMbbhWdlVncL+xDMghhQXg/W64mCex6wPCcdTizZqJOFVO/JydRST4lZET4X1hvOIKaVssVuaQxbszjSAodNBdITHZ528F9k6+NXvqOrgzpDa25Orfbl+yxK9N9EuKgcDYOwpfUhKk9PdESku8zBXeigh5Qewj4h3XYZKWvQIDAQAB" ;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = natUrl + "/alipay/alipayNotifyNotice";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = natUrl + "/alipay/alipayReturnNotice";
    //    public static String return_url = "http://login.calidray.com/?#/sign";
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";*/

    // 应用id 您的appId 收款账号
    public static String app_id = "2021000117691362";

    // 商户私钥 您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcpkAtwUng9+mMANfRCRvVJCUhoeMwUOVICvqDImXAMe8trnLbi3VlIkg07rVlQxlZdetjQ3N+j3saYfpwzmCz+uhl3vj2tZAoaWktNsEC9Nu5DJcs4JzfaFe8Bjf/C7Wl5XVO0S/ElvTS35MME6xlkYFUrUC9mHzjTE8WZ14LOo9LmU7HJcbvZE5JBE38rB55mDNiO8ZuUoPspLw29mpuFVwbxmefCEWTQyokQahLQuLwpcEcyk6tWtDi3cahPKLaGFhObwWHCh2WVnnyZ4GCVUS3pDu7OXDFlbh9lRHGP1LjZpJyACT2d3bvJ+d5vh3D8S8H5mfqxaUGPkG2edTfAgMBAAECggEATA4fvNdKklJrbtD993yvqbVJ3eFml4+/MlI6PK7Xrg/Qa2J2W4eDMN4LJr/7DDYeuaOZ7HDUQ09QWofVey2IH/ojGUq/n7CZVDWRniUmYtQVVev5/gzzvThA2IZAa7D4uZoFcYX45j7QkQ2BrGxk2E9j9oPDmgnOIOFsyJjZPSOn9s/7iB+HjOpeCngO2lFV34zCG3FrnXSDufN6fvlBuhiQYNLaOz0t1Z10kW36fn1XfHTPCsPouW8WFnLSe4aB6ibTDaOYb9Z+ssS60+e6UbbZ24CbB3MEPtcqaefKIDwM7+PdIRwObl1k2lNuayaOyICKqwcxxQ1BB+iXS6kmgQKBgQDJyJ6DIV2gysxk9kLrmqPyJ4QhmF42HEGZItYgRIRSf/cEu83GR94jz4IguCAr+7kOW1hHfgOFXubwt7jvIRjLSSvwl1HN71UkwCx5O3FMYZHcf3D4KExOZ16nGOcI41fn/7fpN7lbrDEe3XbVG2RjYUBh0zyBtXGD0BWd2xEfHwKBgQDGvSQdTPNtrAYq2vDf1WD8Zwo3bH49eAxB4I7gihbiS8QRIF6faKkGaV5VQqCviF15yIKKC2Y4YUXJa2mCwSG26YGwGNRjfEOlkD7DcWuCXH3lugLC6ORmN4l77FuNSoM5tzlyJffY84utrwDSJQbFeMPPztETD16nEDgcJLdSQQKBgAxGkzYhCoQkskiWHr4cm0q5ErXEgj7Ce08oqmQuJuqp9EoqbrtYVfBRyHXbHcHvP3PbJMS8qtQrUNtmIe8SUWLUocKyQfzISaLBujLp5duit+/1JNdEBFhku4Jo5m1I/2kSyop6pGEqWZaqHHecgf/1w6fv5OdzNWOyfg4rbKA7AoGBAMEhvZ7b0RSbP8MV0U80RXQDjmIeBPAnYYIDf9WlXGe9yWP7WPkA9IFOlGW2wgDTPEPFH1aXjVbs8Ky0vWxb0y+17Fn9RykG8ZZLfzLfXeLpj09YXKadPJg5EFplbvL3nl7QN/s9+oXS58J5dwrBALuWiHPCQFuNbCZeFKkE5UIBAoGABq+1gJzuHAABcv/XYtJDRWfrQdc/nY6OiFe5F0OXrPHcFk9berxi6htO7Q7gDpEbAYnDGe32oj+xOyfodtvLSLOzzbKmVc/hhZZV8fh/XwQd4F96UlzCX0OtINK2VqBe+M1Dqfer+i34zjCzlS2rax3n+04EpOkEp98ld0M/e+w=";

    // ֧支付宝公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtjPO+5bJ3kOltDorA46EmvRrJDXb/MaYiAzlNxOwNeUVoF9YBb73lNT/hjPSgQMJCMRSaxN9GHET7lQGrKnQcqv/koJolI6jbBRHFu+Fr3aV3VwVzML/31+tpgvIyUeK/mOgoQTBZuVeZ7DWJhZFljFKASkIv7zxlVhPOoU552AR18jlSKZ4+vOAaZFZDSjGA3sQguLh5kpqdKPPzoJYSEP2LGhknTW8Ej8H9HOfMpDNKqoWjJAMQlVBP19LcqMn/6ttJCigLYCJ+HU9Aota5iez8AeRZHPyY7fh79+dahF6BuqksN9vsH0HHBAt9un10yUZAlhdw3UOaWMONmsmbwIDAQAB";

    // 服务器异步通知页面路径  需要http://格式的完整路径  必须外网可以访问
    public static String notify_url = "http://35466c47.cpolar.io/alipay/alipayNotifyNotice";

    // 页面跳转同步通知页面路径  需要http://格式的完整路径  必须外网可以访问
    public static String return_url = "http://35466c47.cpolar.io/alipay/alipayReturnNotice";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // ֧支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do"; //注意：沙箱测试环境，正式环境为：https://openapi.alipay.com/gateway.do

}
