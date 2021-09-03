
package com.dongl.oss.controller;

import com.dongl.common.base.ResponseParams;
import com.dongl.common.exception.BusinessException;
import com.dongl.common.utils.FileUtil;
import com.dongl.oss.model.OssFileConfig;
import com.dongl.oss.service.IOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@RequestMapping("/ossFiles")
@Slf4j
public class OssFileController {

    @Autowired private IOssService ossService;

    /** 上传文件 （单文件上传） */
    @PostMapping("/fileUpload/{bizType}")
    public ResponseParams singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("bizType") String bizType) {
        if( file == null ) {
            return  new ResponseParams().error("10010", "选择文件不存在");
        }
        try {
            OssFileConfig ossFileConfig = OssFileConfig.getOssFileConfigByBizType(bizType);

            //1. 判断bizType 是否可用
            if(ossFileConfig == null){
                throw new BusinessException( "10001" ,"类型有误");
            }

            // 2. 判断文件是否支持
            String fileSuffix = FileUtil.getFileSuffix(file.getOriginalFilename(), false);
            if( !ossFileConfig.isAllowFileSuffix(fileSuffix) ){
                throw new BusinessException("10004","上传文件格式不支持！");
            }

            // 3. 判断文件大小是否超限
            if( !ossFileConfig.isMaxSizeLimit(file.getSize()) ){
                throw new BusinessException("10005" , "上传大小请限制在["+ossFileConfig.getMaxSize() / 1024 / 1024 +"M]以内！");
            }

            // 新文件地址 (xxx/xxx.jpg 格式)
            String saveDirAndFileName = bizType + "/" + UUID.randomUUID() + "." + fileSuffix;
            String url = ossService.upload2PreviewUrl(ossFileConfig.getOssSavePlaceEnum(), file, saveDirAndFileName);
            return new ResponseParams().success(url);

        } catch (BusinessException biz) {
            throw biz;
        } catch (Exception e) {
            log.error("upload error, fileName = {}", file.getOriginalFilename(), e);
            throw new BusinessException("110120" , "系统异常");
        }
    }

}
