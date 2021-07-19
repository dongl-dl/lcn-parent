package com.dongl.alipay.config;


/**
 * 支付宝支付基础配置类
 *  设置账户相关信息 以及 返回路径
 * https://openhome.alipay.com/platform/appDaily.htm
 *  *        https://opendocs.alipay.com/open/00uk9e
 *  *        https://opendocs.alipay.com/open/270/105899
 *  *        https://opensupport.alipay.com/support/helpcenter/98/201602580747?ant_source=opendoc_recommend
 */

public class AlipayConfig {

	// 应用id 您的appId 收款账号
	public static String app_id = "2021000117691362";

	// 商户私钥 您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcpkAtwUng9+mMANfRCRvVJCUhoeMwUOVICvqDImXAMe8trnLbi3VlIkg07rVlQxlZdetjQ3N+j3saYfpwzmCz+uhl3vj2tZAoaWktNsEC9Nu5DJcs4JzfaFe8Bjf/C7Wl5XVO0S/ElvTS35MME6xlkYFUrUC9mHzjTE8WZ14LOo9LmU7HJcbvZE5JBE38rB55mDNiO8ZuUoPspLw29mpuFVwbxmefCEWTQyokQahLQuLwpcEcyk6tWtDi3cahPKLaGFhObwWHCh2WVnnyZ4GCVUS3pDu7OXDFlbh9lRHGP1LjZpJyACT2d3bvJ+d5vh3D8S8H5mfqxaUGPkG2edTfAgMBAAECggEATA4fvNdKklJrbtD993yvqbVJ3eFml4+/MlI6PK7Xrg/Qa2J2W4eDMN4LJr/7DDYeuaOZ7HDUQ09QWofVey2IH/ojGUq/n7CZVDWRniUmYtQVVev5/gzzvThA2IZAa7D4uZoFcYX45j7QkQ2BrGxk2E9j9oPDmgnOIOFsyJjZPSOn9s/7iB+HjOpeCngO2lFV34zCG3FrnXSDufN6fvlBuhiQYNLaOz0t1Z10kW36fn1XfHTPCsPouW8WFnLSe4aB6ibTDaOYb9Z+ssS60+e6UbbZ24CbB3MEPtcqaefKIDwM7+PdIRwObl1k2lNuayaOyICKqwcxxQ1BB+iXS6kmgQKBgQDJyJ6DIV2gysxk9kLrmqPyJ4QhmF42HEGZItYgRIRSf/cEu83GR94jz4IguCAr+7kOW1hHfgOFXubwt7jvIRjLSSvwl1HN71UkwCx5O3FMYZHcf3D4KExOZ16nGOcI41fn/7fpN7lbrDEe3XbVG2RjYUBh0zyBtXGD0BWd2xEfHwKBgQDGvSQdTPNtrAYq2vDf1WD8Zwo3bH49eAxB4I7gihbiS8QRIF6faKkGaV5VQqCviF15yIKKC2Y4YUXJa2mCwSG26YGwGNRjfEOlkD7DcWuCXH3lugLC6ORmN4l77FuNSoM5tzlyJffY84utrwDSJQbFeMPPztETD16nEDgcJLdSQQKBgAxGkzYhCoQkskiWHr4cm0q5ErXEgj7Ce08oqmQuJuqp9EoqbrtYVfBRyHXbHcHvP3PbJMS8qtQrUNtmIe8SUWLUocKyQfzISaLBujLp5duit+/1JNdEBFhku4Jo5m1I/2kSyop6pGEqWZaqHHecgf/1w6fv5OdzNWOyfg4rbKA7AoGBAMEhvZ7b0RSbP8MV0U80RXQDjmIeBPAnYYIDf9WlXGe9yWP7WPkA9IFOlGW2wgDTPEPFH1aXjVbs8Ky0vWxb0y+17Fn9RykG8ZZLfzLfXeLpj09YXKadPJg5EFplbvL3nl7QN/s9+oXS58J5dwrBALuWiHPCQFuNbCZeFKkE5UIBAoGABq+1gJzuHAABcv/XYtJDRWfrQdc/nY6OiFe5F0OXrPHcFk9berxi6htO7Q7gDpEbAYnDGe32oj+xOyfodtvLSLOzzbKmVc/hhZZV8fh/XwQd4F96UlzCX0OtINK2VqBe+M1Dqfer+i34zjCzlS2rax3n+04EpOkEp98ld0M/e+w=";

	// ֧支付宝公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtjPO+5bJ3kOltDorA46EmvRrJDXb/MaYiAzlNxOwNeUVoF9YBb73lNT/hjPSgQMJCMRSaxN9GHET7lQGrKnQcqv/koJolI6jbBRHFu+Fr3aV3VwVzML/31+tpgvIyUeK/mOgoQTBZuVeZ7DWJhZFljFKASkIv7zxlVhPOoU552AR18jlSKZ4+vOAaZFZDSjGA3sQguLh5kpqdKPPzoJYSEP2LGhknTW8Ej8H9HOfMpDNKqoWjJAMQlVBP19LcqMn/6ttJCigLYCJ+HU9Aota5iez8AeRZHPyY7fh79+dahF6BuqksN9vsH0HHBAt9un10yUZAlhdw3UOaWMONmsmbwIDAQAB";

	// 服务器异步通知页面路径  需要http://格式的完整路径  必须外网可以访问
	public static String notify_url = "http://localhost:1008/pay/alipay_notify";

	// 页面跳转同步通知页面路径  需要http://格式的完整路径  必须外网可以访问
	public static String return_url = "http://localhost:1008/pay_log/list";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// ֧支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

}