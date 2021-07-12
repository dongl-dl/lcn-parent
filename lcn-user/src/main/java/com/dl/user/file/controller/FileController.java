package com.dl.user.file.controller;

import com.dl.user.file.FastDFSFile;
import com.dl.user.file.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.dl.user.util.file.FileType.getFileSuffix;

/**
 * 描述
 *
 * @author
 * @version 1.0
 * @package com.changgou.file.controller *
 * @since 1.0
 */
@Slf4j
@RestController
@CrossOrigin//支持跨域
// 跨域:
// 不同的域名A 访问 域名B 的数据就是跨域
// 端口不同 也是跨域  loalhost:18081----->localhost:18082
// 协议不同 也是跨域  http://www.jd.com  --->  https://www.jd.com
// 域名不同 也是跨域  http://www.jd.com  ---> http://www.taobao.com
// 协议一直,端口一致,域名一致就不是跨域  http://www.jd.com:80 --->http://www.jd.com:80 不是跨域
public class FileController {

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 返回文件的全路径
     *
     * @param file 页面的文件对象
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file") MultipartFile file) {
        try {
            //1. 创建图片文件对象(封装)
            //2. 调用工具类实现图片上传
            FastDFSFile fastdfsfile = new FastDFSFile(
                    file.getOriginalFilename(),//原来的文件名  1234.jpg
                    file.getBytes(),//文件本身的字节数组
                    StringUtils.getFilenameExtension(file.getOriginalFilename())
            );
            String[] upload = fastDFSClient.upload(fastdfsfile);
            //  upload[0] group1
            //  upload[1] M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg
            //3. 拼接图片的全路径返回
            // http://192.168.10.25:80/group1/M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg
            return fastDFSClient.getTrackerUrl()+"/"+upload[0]+"/"+upload[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件删除
     * @param groupName
     * @param remoteFileName
     * @return
     */
    @GetMapping("/deleteFile")
    public String deletePic(String groupName , String remoteFileName){
        fastDFSClient.deleteFile(groupName , remoteFileName);
        return "success";
    }

    /**
     * 文件下载接口
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadFile",method = RequestMethod.GET)
    public void downLoad(String groupName , String remoteFileName , HttpServletResponse response){
//        StorageServer storages = fastDFSClient.getStorages(groupName);
//        FileInfo file = fastDFSClient.getFile(groupName, remoteFileName);
//        ServerInfo[] serverInfo = fastDFSClient.getServerInfo(groupName, remoteFileName);
        InputStream inputStream = fastDFSClient.downFile(groupName, remoteFileName);
        String fileSuffix = getFileSuffix(inputStream);
        BufferedInputStream br = new BufferedInputStream(inputStream);
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset(); // 非常重要
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + "fastDFS."+fileSuffix);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            log.error("文件下载失败，失败信息：{}" , e.getMessage());
        }finally {
            try {
                br.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
