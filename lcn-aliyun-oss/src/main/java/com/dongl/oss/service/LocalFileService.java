package com.dongl.oss.service;

import com.dongl.oss.config.OssConfig;
import com.dongl.oss.constant.OssSavePlaceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
@ConditionalOnProperty(name = "oss.service-type", havingValue = "local")
public class LocalFileService implements IOssService{

    @Autowired private OssConfig ossConfig;

    @Override
    public String upload2PreviewUrl(OssSavePlaceEnum ossSavePlaceEnum, MultipartFile multipartFile, String saveDirAndFileName) {

        try {

            String savePath = ossSavePlaceEnum ==
                    OssSavePlaceEnum.PUBLIC ? ossConfig.getFilePublicPath() : ossConfig.getFilePrivatePath();

            File saveFile = new File(savePath + File.separator + saveDirAndFileName);

            //如果文件夹不存在则创建文件夹
            File dir = saveFile.getParentFile();
            if(!dir.exists()) {
                dir.mkdirs();
            }
            multipartFile.transferTo(saveFile);

        } catch (Exception e) {

            log.error("文件上传本地出错--------------------", e);
        }

        // 私有文件 不返回预览文件地址
        if(ossSavePlaceEnum == OssSavePlaceEnum.PRIVATE){
            return saveDirAndFileName;
        }

        return "" + "/" + saveDirAndFileName;
    }

    @Override
    public boolean downloadFile(OssSavePlaceEnum ossSavePlaceEnum, String source, String target) {
        return false;
    }
}
