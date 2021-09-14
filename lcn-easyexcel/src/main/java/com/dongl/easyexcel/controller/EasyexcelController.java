package com.dongl.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.dongl.easyexcel.domian.Customer;
import com.dongl.easyexcel.domian.DemoMergeData;
import com.dongl.easyexcel.domian.DownloadData;
import com.dongl.easyexcel.domian.UploadData;
import com.dongl.easyexcel.utils.excel.EasyExcelUtils;
import com.dongl.easyexcel.utils.excel.UploadDataListener;
import com.dongl.easyexcel.utils.excel.UploadMapperUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName EasyexcelController.java
 * @Description easy-excel 导入导出控制层
 * @createTime 2021-09-14 11:27:00
 */
@RestController
@RequestMapping("easy-excel")
public class EasyexcelController {

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * 1. 创建excel对应的实体对象 参照{@link DownloadData}
     * 2. 设置返回的 参数
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        EasyExcelUtils.exportExcelDataManualLock(response , "测试" , "sheet" , DownloadData.class , this.data());

//        Set<String> excludeColumnFiledNames = new HashSet<String>();
//        excludeColumnFiledNames.add("date");
//        EasyExcelUtils.exportExcelDataInclude(response , "测试" , "sheet001" , DownloadData.class , this.data() ,excludeColumnFiledNames );

//        EasyExcelUtils.imageWrite(response);


//        EasyExcelUtils.textAndImageWrite(response);


        //注解方法 参考 DemoMergeData.class
//        EasyExcelUtils.mergeWriteCustomize(response , "测试212121121212" , "sheet00111" , DemoMergeData.class , this.data() );
    }

    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @GetMapping("downloadFailedUsingJson")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DownloadData.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 文件上传
     * 1. 创建excel对应的实体对象 参照{@link UploadData}
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * 3. 直接读即可
     * @file 上传文件   UploadData.class ：实体类  method: 插入数据库方法名称 与 UploadDAO {@link UploadMapperUtils}中操作数据库的方法对应
     */
    @PostMapping("upload")
    public String upload(MultipartFile file){
        String method = "saveExcelList01";
        EasyExcelUtils.uploadExcelData(file , Customer.class , method , 1);
        return "success";
    }

    private List<DownloadData> data() {
        List<DownloadData> list = new ArrayList<DownloadData>();
        for (int i = 0; i < 10; i++) {
            DownloadData data = new DownloadData();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }



    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<String>();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<String>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
    }

}
