
package com.dongl.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;

import com.dongl.oss.config.AliyunOssConfig;
import com.dongl.oss.constant.OssSavePlaceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;

/**
* 阿里云OSS 实现类
*
* @author D-L
* @date 2021/09/02 21:20
*/
@Service
@Slf4j
@ConditionalOnProperty(name = "oss.service-type", havingValue = "aliyun-oss")
public class AliyunOssService implements IOssService{

    @Autowired private AliyunOssConfig aliyunOssConfig;

    // ossClient 初始化
    private OSS ossClient = null;

    @PostConstruct
    public void init(){
        ossClient = new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessKeyId(), aliyunOssConfig.getAccessKeySecret());
    }

    @Override
    public String upload2PreviewUrl(OssSavePlaceEnum ossSavePlaceEnum, MultipartFile multipartFile, String saveDirAndFileName) {
        try {
            this.ossClient.putObject(aliyunOssConfig.getPublicBucketName(), saveDirAndFileName, multipartFile.getInputStream());
            if(ossSavePlaceEnum == OssSavePlaceEnum.PUBLIC){
                return "https://" + aliyunOssConfig.getPublicBucketName() + "." + aliyunOssConfig.getEndpoint() + "/" + saveDirAndFileName;
            }
            return saveDirAndFileName;
        } catch (Exception e) {
           log.error("error", e);
            return null;
        }
    }

    @Override
    public boolean downloadFile(OssSavePlaceEnum ossSavePlaceEnum, String source, String target) {
        try {
            String bucket = ossSavePlaceEnum == OssSavePlaceEnum.PRIVATE ? aliyunOssConfig.getPrivateBucketName() : aliyunOssConfig.getPublicBucketName();
            this.ossClient.getObject(new GetObjectRequest(bucket, source), new File(target));
            return true;
        } catch (Exception e) {
            log.error("error", e);
            return false;
        }
    }
}
