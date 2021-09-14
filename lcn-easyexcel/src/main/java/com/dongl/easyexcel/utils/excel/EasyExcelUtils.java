package com.dongl.easyexcel.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dongl.easyexcel.domian.DemoMergeData;
import com.dongl.easyexcel.domian.ImageData;
import com.dongl.easyexcel.domian.TextImageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author D-L
 * @program: easy-excel
 * @description: excel文件上传下载公用类
 * @date 2021-09-14 14:10:54
 */
@Slf4j
@Component
public class EasyExcelUtils {
    @Resource
    private UploadMapperUtils uploadMapperUtils;

    //处理static方法无法使用 注入的 easyExcelUtils
    private static EasyExcelUtils easyExcelUtils;

    @PostConstruct
    public void init() {
        easyExcelUtils = this;
        easyExcelUtils.uploadMapperUtils = this.uploadMapperUtils;
    }


//------------------------------------------------------下载Excel文件------------------------------------------------------------//
    /**
     * 下载Excel文件 写法1
     * @param response
     * @param excelName Excel文件名称
     * @param sheetName 工作表名称
     * @param data 填充到excel的数据集
     * @param <T> 数据集对应的实体类
     */
    public static<T> void exportExcelData(HttpServletResponse response , String excelName , String sheetName , Class clazz , List<T> data){
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        try {
            EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
        }catch (Exception e){
            log.error("下载Excel文件失败----------------------------文件名：{}" + excelName);
        }
    }

    /**
     * 下载Excel文件 写法2
     * @param response
     * @param excelName
     * @param sheetName
     * @param clazz
     * @param data
     * @param <T>
     */
    public static<T> void exportExcelDataManualLock(HttpServletResponse response , String excelName , String sheetName , Class clazz , List<T> data){
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write( response.getOutputStream(), clazz).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            excelWriter.write(data, writeSheet);
        }catch (Exception e){
            log.error("下载Excel文件失败----------------------------文件名：{}" + excelName);
        }finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 根据用户传入字段 假设我们要忽略 date
     *
     *                  Set<String> excludeColumnFiledNames = new HashSet<String>();
     *                  excludeColumnFiledNames.add("date");
     * @param response
     * @param excelName
     * @param sheetName
     * @param clazz
     * @param data
     * @param excludeColumnFiledNames
     * @param <T>
     */
    public static<T> void exportExcelDataExclude (HttpServletResponse response , String excelName , String sheetName ,
                                                  Class clazz , List<T> data , Set<String> excludeColumnFiledNames){
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        try {
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(response.getOutputStream(), clazz).excludeColumnFiledNames(excludeColumnFiledNames).sheet(sheetName).doWrite(data);
        }catch (Exception e){
            log.error("下载Excel文件失败----------------------------文件名：{}" + excelName);
        }
    }

    /**
     * 根据用户传入字段 假设我们只要导出 date
     *
     *             Set<String> includeColumnFiledNames = new HashSet<String>();
     *             includeColumnFiledNames.add("date");
     * @param response
     * @param excelName
     * @param sheetName
     * @param clazz
     * @param data
     * @param <T>
     */
    public static<T> void exportExcelDataInclude (HttpServletResponse response , String excelName , String sheetName ,
                                                  Class clazz , List<T> data , Set<String> includeColumnFiledNames){
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        try {
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(response.getOutputStream(), clazz).includeColumnFiledNames(includeColumnFiledNames).sheet("模板").doWrite(data);
            ServletOutputStream outputStream = response.getOutputStream();
            System.out.println("下载Excel文件成功----------------------------！！！！");
        }catch (Exception e){
            log.error("下载Excel文件失败----------------------------文件名：{}" + excelName);
        }
    }


    //@Data
    //public class ConverterData {
    //    /**
    //     * 我想所有的 字符串起前面加上"自定义："三个字
    //     */
    //    @ExcelProperty(value = "字符串标题", converter = CustomStringStringConverter.class)
    //    private String string;
    //    /**
    //     * 我想写到excel 用年月日的格式
    //     */
    //    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    //    @ExcelProperty("日期标题")
    //    private Date date;
    //    /**
    //     * 我想写到excel 用百分比表示
    //     */
    //    @NumberFormat("#.##%")
    //    @ExcelProperty(value = "数字标题")
    //    private Double doubleData;
    //}

    /**
     * 下载Excel文件 (日期、数字或者自定义格式转换)
     *
     * @param response
     * @param excelName
     * @param sheetName
     * @param clazz
     * @param data
     * @param <T>
     */
    public static<T> void converterWrite(HttpServletResponse response , String excelName , String sheetName , Class clazz , List<T> data ){
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        try {
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(response.getOutputStream(), clazz/*ConverterData.class*/).sheet("模板").doWrite(data);
        }catch (Exception e){
            log.error("下载Excel文件失败----------------------------文件名：{}" + excelName);
        }
    }

    /**
     * 图片导出
     * @param response
     */
    public static void imageWrite(HttpServletResponse response){
        // 如果使用流 记得关闭
        InputStream inputStream = null;
        try {
            List<ImageData> list = new ArrayList<ImageData>();
            ImageData imageData = new ImageData();

            String imagePath = "D:\\tool\\ideapicture" + File.separator + "2.jpg";
            // 放入五种类型的图片 实际使用只要选一种即可
            imageData.setFile(new File(imagePath));
            imageData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
            imageData.setString(imagePath);
            inputStream = FileUtils.openInputStream(new File(imagePath));
            imageData.setInputStream(inputStream);
            imageData.setUrl(new URL("https://ali.image.hellorf.com/images/ef3be79d2432277bd70654839699141e.jpeg"));

            list.add(imageData);
            EasyExcel.write(response.getOutputStream(), ImageData.class).sheet().doWrite(list);
        }catch (Exception e){
            log.error("Excel图片导出出错----------------------------------------");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文本+图片 导出
     * @param response
     */
    public static void textAndImageWrite(HttpServletResponse response){
        // 如果使用流 记得关闭
        InputStream inputStream = null;
        try {
            List<TextImageData> list = new ArrayList<TextImageData>();
            TextImageData imageData = new TextImageData();

            String imagePath = "D:\\tool\\ideapicture" + File.separator + "2.jpg";
            // 放入五种类型的图片 实际使用只要选一种即可
            imageData.setFile(new File(imagePath));
            imageData.setUrl("https://ali.image.hellorf.com/images/ef3be79d2432277bd70654839699141e.jpeg");
            imageData.setName("测试文件");
            list.add(imageData);
            EasyExcel.write(response.getOutputStream(), TextImageData.class).sheet().doWrite(list);
        }catch (Exception e){
            log.error("Excel图片导出出错----------------------------------------");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     *  合并单元格 方法1 注解
     * @param response
     * @param excelName
     * @param sheetName
     * @param clazz     实体类参考 DemoMergeData {@link DemoMergeData}
     * @param data
     * @param <T>
     */
    public static<T> void mergeWriteAnnotation(HttpServletResponse response , String excelName , String sheetName , Class clazz , List<T> data){
        // 方法1 注解
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        try {
            // 在DemoStyleData里面加上ContentLoopMerge注解
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
        }catch (Exception e){
            log.error("下载Excel文件合并单元格-注解失败----------------------------文件名：{}" + excelName);
        }
    }

    /**
     * 合并单元格  方法2 自定义合并单元格策略
     * @param response
     * @param excelName
     * @param sheetName
     * @param clazz
     * @param data
     * @param <T>
     */
    public static<T> void mergeWriteCustomize(HttpServletResponse response , String excelName , String sheetName , Class clazz , List<T> data){
        // 方法2 自定义合并单元格策略
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        try {
//            LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 1);
            //row:行   column：列  自定义合并
            OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(1,2,0,0);
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(response.getOutputStream(), clazz).registerWriteHandler(onceAbsoluteMergeStrategy).sheet(sheetName).doWrite(data);
        }catch (Exception e){
            log.error("下载Excel文件合并单元格-自定义失败----------------------------文件名：{}" + excelName);
        }
    }

    /**
     * 不创建对象的写
     * @param response
     * @param excelName
     * @param sheetName
     * @param head
     * @param dataList
     */
    public static void noModelWrite(HttpServletResponse response , String excelName , String sheetName , List<List<String>> head , List<List<Object>> dataList){
        //处理并读取文件类型和编码文件后缀
        processFileEncodingSuffix(response , excelName);
        try {
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(response.getOutputStream()).head(head).sheet("模板").doWrite(dataList);
        }catch (Exception e){
            log.error("Excel不创建对象下载------------------文件名称：{}" + excelName);
        }
    }

//--------------------------------------------上传Excel文件-------------------------------------------------//
    /**
     * 上传Excel文件(不创建对象)
     * @param file 导入文件
     * @param method {@link UploadMapperUtils} 对应 UploadMapperUtils 操作数据库的方法
     * @param headRowNumber 从第几行开始读取
     */
    public static void noModelRead(MultipartFile file, String method , Integer headRowNumber){
        UploadDataListener uploadDataListener = new UploadDataListener(easyExcelUtils.uploadMapperUtils,method);
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        try {
            EasyExcel.read(file.getInputStream(), uploadDataListener)
                    .sheet()
                    .headRowNumber(headRowNumber)
                    .doRead();
        }catch (Exception e) {
            e.printStackTrace();
            log.error("解析excel文件并插入数据库失败 , 上传实体类对象--------------------- : {}" + file.getName());
        }
    }



    /**
     * 上传Excel文件
     * @param file 导入文件
     * @param clazz 对应数据库实体类class
     * @param method {@link UploadMapperUtils} 对应 UploadMapperUtils 操作数据库的方法
     * @param headRowNumber 从第几行开始读取
     */
    public static void uploadExcelData(MultipartFile file, Class clazz , String method , Integer headRowNumber){
        UploadDataListener uploadDataListener = new UploadDataListener(easyExcelUtils.uploadMapperUtils,method);
        try {
            // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
            EasyExcel.read(file.getInputStream(), clazz, uploadDataListener).sheet().headRowNumber(headRowNumber).doRead();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("解析excel文件并插入数据库失败 , 上传实体类对象--------------------- : {}" + clazz.toString());
        }
    }

    /**
     * 上传Excel文件 (包含额外信息  批注 超链接 合并单元格信息)
     * @param file 导入文件
     * @param clazz 对应数据库实体类class
     * @param method {@link UploadMapperUtils} 对应 UploadMapperUtils 操作数据库的方法
     * @param headRowNumber 从第几行开始读取
     */
    public static void uploadExcelDataWithExtra(MultipartFile file, Class clazz , String method , Integer headRowNumber){
        UploadDataListener uploadDataListener = new UploadDataListener(easyExcelUtils.uploadMapperUtils,method);
        try {
            // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
            EasyExcel.read(file.getInputStream(), clazz, uploadDataListener)
                    // 需要读取批注 默认不读取
                    .extraRead(CellExtraTypeEnum.COMMENT)
                    // 需要读取超链接 默认不读取
                    .extraRead(CellExtraTypeEnum.HYPERLINK)
                    // 需要读取合并单元格信息 默认不读取
                    .extraRead(CellExtraTypeEnum.MERGE)
                    .sheet()
                    .headRowNumber(headRowNumber)
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("解析excel文件并插入数据库失败 , 上传实体类对象--------------------- : {}" + clazz.toString());
        }
    }

    /**
     * 上传Excel文件 (读取全部sheet)
     * @param file 导入文件
     * @param clazz 对应数据库实体类class
     * @param method {@link UploadMapperUtils} 对应 UploadMapperUtils 操作数据库的方法
     */
    public static void  repeatedReadExcelData(MultipartFile file, Class clazz , String method ){
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个uploadDataListener里面写
        UploadDataListener uploadDataListener = new UploadDataListener(easyExcelUtils.uploadMapperUtils,method);
        try {
            EasyExcel.read(file.getInputStream(), clazz, uploadDataListener).doReadAll();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("解析excel文件全部sheet并插入数据库失败 , 上传实体类对象--------------------- : {}" + clazz.toString());
        }

    }

    /**
     * read_part_of_the_sheet
     * @param file 导入文件
     * @param clazz 对应数据库实体类class
     * @param method {@link UploadMapperUtils} 对应 UploadMapperUtils 操作数据库的方法
     */
    public static void readExcelPartSheet(MultipartFile file, Class clazz , String method){
        // 读取部分sheet
        UploadDataListener uploadDataListener = new UploadDataListener(easyExcelUtils.uploadMapperUtils,method);
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(file.getInputStream()).build();
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 = EasyExcel.readSheet(0).head(clazz).registerReadListener(uploadDataListener).build();
            ReadSheet readSheet2 = EasyExcel.readSheet(1).head(clazz).registerReadListener(uploadDataListener).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        }catch (Exception e){
            log.error("Excel读取部分sheet-------------------------------文件名称：{}"+ file.getName());
        }finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }


    /**
     * 处理并读取文件类型和编码文件后缀
     * Process and read file type and encoding File suffix
     * @param response
     * @param excelName
     */
    public static void processFileEncodingSuffix(HttpServletResponse response , String excelName){
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            String fileName = URLEncoder.encode(excelName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        }catch (Exception e){
            e.printStackTrace();
            log.error("处理导出文件类型编码以及文件后缀名有误----------------------");
        }
    }
}