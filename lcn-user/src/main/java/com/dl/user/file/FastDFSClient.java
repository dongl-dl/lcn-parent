package com.dl.user.file;


import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述
 *
 * @author
 * @version 1.0
 * @package com.changgou.util *
 * @since 1.0
 */
@Component
@Slf4j
public class FastDFSClient {

    @Value("${fastdfs.tracker_servers}")
    private String tracker_servers;

    @Value("${fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout;

    @Value("${fastdfs.network_timeout_in_seconds}")
    private int network_timeout;

    @Value("${fastdfs.charset}")
    private String charset;

//    @Value("fastdfs.tracker_http_port")
    private int tracker_http_port=80;

    /**
     * 初始化fastDFS的环境
     */
    private void initFdfsConfig() {
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout);
            ClientGlobal.setG_network_timeout(network_timeout);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_tracker_http_port(tracker_http_port);
        } catch (Exception e) {
            log.error("初始化fastDFS的环境失败~");
        }
    }

    /**
     * 图片上传至fastDFS
     * @param file
     * @return
     */
    public String[] upload(FastDFSFile file) {
        // 1. 初始化fastDFS的环境
        initFdfsConfig();
        try {
            // 2. 获取trackerClient服务
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3.获取storageClient
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //参数1 字节数组
            //参数2 扩展名(不带点)
            //参数3 元数据( 文件的大小,文件的作者,文件的创建时间戳)
            NameValuePair[] meta_list = new NameValuePair[]{new NameValuePair(file.getAuthor()), new NameValuePair(file.getName())};
            String[] strings = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
            return strings;// strings[0]==group1  strings[1]=M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 图片下载
     */
    public InputStream downFile(String groupName, String remoteFileName) {
        initFdfsConfig();
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            //3.创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //5.创建stroageserver 对象
            //6.创建storageclient 对象
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //7.根据组名 和 文件名 下载图片

            //参数1:指定组名
            //参数2 :指定远程的文件名
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            return byteArrayInputStream;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 文件删除
     */
    public void deleteFile(String groupName, String remoteFileName) {
        // 1. 初始化fastDFS的环境
        initFdfsConfig();
        try {
            //3.创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //5.创建stroageserver 对象
            //6.创建storageclient 对象
            StorageClient storageClient = new StorageClient(trackerServer, null);
            int i = storageClient.delete_file(groupName, remoteFileName);
            if (i == 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据组名获取组的信息
     * @param groupName
     * @return
     */
    public StorageServer getStorages(String groupName) {
        initFdfsConfig();
        try {
            TrackerClient trackerClient = new TrackerClient();
            //创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //参数1 指定traqckerserver 对象
            //参数2 指定组名
            StorageServer group1 = trackerClient.getStoreStorage(trackerServer, groupName);
            return group1;
        } catch (IOException e) {
            log.info("根据组名获取组的信息" + e.getMessage());
        }
        return null;
    }


    /**
     * 根据文件名和组名获取文件的信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public FileInfo getFile(String groupName, String remoteFileName) {
        initFdfsConfig();
        try {
            TrackerClient trackerClient = new TrackerClient();
            //创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //参数1 指定组名
            //参数2 指定文件的路径
            FileInfo fileInfo = storageClient.get_file_info(groupName, remoteFileName);
            return fileInfo;
        } catch (Exception e) {
            log.info("根据文件名和组名获取文件的信息" + e.getMessage());
        }
        return null;
    }


    /**
     * 根据文件名和组名 获取组信息的数组信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public ServerInfo[] getServerInfo(String groupName, String remoteFileName){
        initFdfsConfig();
        try {
            //创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
            return group1s;
        } catch (IOException e) {
            log.info("根据文件名和组名 获取组信息的数组信息" + e.getMessage());
        }
        return null;

    }

    /**
     * 获取tracker 的ip和端口的信息
     * @return
     */
    public String getTrackerUrl(){
        try {
            //创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //tracker 的ip的信息
            String hostString = trackerServer.getInetSocketAddress().getHostString();
            //http://192.168.10.25:80/group1/M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg img
            int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
            return "http://" + hostString + ":" + g_tracker_http_port;
        } catch (IOException e) {
          log.info("获取tracker的ip和端口的信息失败" + e.getMessage());
        }
        return null;
    }
}
