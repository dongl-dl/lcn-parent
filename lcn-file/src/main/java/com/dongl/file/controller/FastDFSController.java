package com.dongl.file.controller;

import com.dongl.file.service.FastDFSClient;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName FastDFSController.java
 * @Description fastDFS文件上传下载
 * @createTime 2021-07-07 10:55:00
 */
@Slf4j
@RestController
@RequestMapping("/fastDFS")
public class FastDFSController {
    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file){
        String result ="";
        try {
            byte[] bytes = file.getBytes();
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            long fileSize = file.getSize();
            result = fastDFSClient.uploadFile(bytes, fileSize, extension);
        }catch (Exception e) {
            log.error("fastDFS 文件上传失败------fileName:{}" , file.getName());
        }
        return result;
    }

    /**
     *
     * @param fileUrl  group1/M00/00/00/wKgKGWDlIo-AKfngAAFlGYb7GuA61.xlsx
     * @param response
     */
    @GetMapping("/download")
    public void downloadFile(String fileUrl, HttpServletResponse response){
        String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
        byte[] bytes = new byte[0];
        try {
            bytes = fastDFSClient.downloadFile(fileUrl);
            response.reset(); // 非常重要
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("fastDFS" + suffix, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
        } catch (IOException e) {
            log.error("fastDFS 下载文件失败------fileUrl:{}" , fileUrl);
        }
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("uploadFile1")
    public String uploadFile1(MultipartFile file){
        // 元数据
        Set<MetaData> metaDataSet = new HashSet<MetaData>();
        metaDataSet.add(new MetaData("Author", "dongl"));
        metaDataSet.add(new MetaData("CreateDate", new Date().toString()));
        //获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //上传文件
        StorePath uploadFile = null;
        try {
            uploadFile = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, metaDataSet);
        } catch (Exception e) {
            log.info("fastDFS文件上传失败----");
        }
        return uploadFile.getFullPath();
    }

    @RequestMapping("/down")
    public ResponseEntity<byte[]> down(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode("fastDFS" + suffix, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] result = fastFileStorageClient.downloadFile(group, path, downloadByteArray);
        return new ResponseEntity<>(result,headers, HttpStatus.OK);
    }
}
