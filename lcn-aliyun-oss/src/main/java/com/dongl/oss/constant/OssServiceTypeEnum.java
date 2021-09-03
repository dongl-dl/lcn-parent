package com.dongl.oss.constant;

import lombok.Getter;


@Getter
public enum OssServiceTypeEnum {

    LOCAL("local"), //本地存储

    ALIYUN_OSS("aliyun-oss");  //阿里云oss

    /** 名称 **/
    private String serviceName;

    OssServiceTypeEnum(String serviceName){
        this.serviceName = serviceName;
    }
}
