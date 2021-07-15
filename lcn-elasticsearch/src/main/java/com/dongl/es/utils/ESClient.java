package com.dongl.es.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName EsUtil.java
 * @Description TODO
 * @createTime 2021-07-15 15:11:00
 */
@Component
public class ESClient {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private final String STATUS = "OK";

    /**
     * 创建索引
     *
     * @param indexs 索引名称
     * @return true 创建成功,false创建失败
     */
    private boolean createIndex(String... indexs) throws Exception {
        for (String index : indexs) {
            GetIndexRequest getIndexRequest = new GetIndexRequest(index);
            if (restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT)) {
                throw new RuntimeException("索引:" + index + "已存在");
            }
        }
        for (String index : indexs) {
            //创建索引请求
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
            //创建执行请求
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }
        return true;
    }

    /**
     * 查看索引是否存在
     *
     * @return true存在 false不存在
     * @throws IOException es连接异常
     */
    private boolean getIndex(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @return true删除成功 false删除失败或索引不存在
     */
    public boolean deleteIndex(String index) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        try {
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 插入数据
     *
     * @param index 索引名称
     * @param data  数据 可以为单个对象也可以为数组
     * @return true 插入成功 false插入失败
     * @throws IOException es连接异常
     */
    public boolean insertData(String index, String id, Object... data) throws Exception {
        boolean flag = getIndex(index);
        if (!flag) {
            createIndex(index);
        }
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("15s");
        for (int i = 0; i < data.length; i++) {
            bulkRequest.add(new IndexRequest(index).id(id).source(JSON.toJSONString(data[i]), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        //是否失败 bulk.hasFailures()=false 代表成功
        return !bulk.hasFailures();
    }

    /**
     * 修改数据
     *
     * @param index 索引
     * @param id    esId
     * @param data  修改后的数据
     * @return true修改成功 false修改失败
     * @throws IOException es连接异常
     */
    public boolean updateData(String index, String id, Object data) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.timeout("1s");
        UpdateRequest doc = updateRequest.doc(JSON.toJSONString(data), XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return STATUS.equals(update.status().toString());
    }

    /**
     * @param index 索引
     * @param id    esId
     * @return true删除成功 false删除失败
     * @throws IOException es连接异常
     */
    public boolean deleteData(String index, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        deleteRequest.timeout("1s");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return STATUS.equals(deleteResponse.status().toString());
    }

    /**
     * 根据分页查询数据 查询全部
     *
     * @param index 索引名称
     * @param form  页数 如果查看第一页传-1
     * @param size  数量 默认传10
     * @return 查询好的SearchHit数组
     * @throws IOException es连接异常
     */
    public SearchHits queryPage(String index, Integer form, Integer size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //QueryBuilders.matchAllQuery查询所有
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(matchAllQueryBuilder);
        //构建分页
        if (form == -1) {
            searchSourceBuilder.from();
        } else {
            searchSourceBuilder.from(form);
        }
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search.getHits();
    }

    /**
     * 精确查找
     *
     * @param index 索引名称
     * @param form  页数 如果查看第一页传-1
     * @param size  数量 默认传10
     * @param name  查询参数名
     * @param value 查询值
     * @return 查询好的SearchHit数组
     * @throws IOException es连接异常
     */
    public SearchHit[] queryPage(String index, Integer form, Integer size, String name, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //QueryBuilders.termQuery 精确查找
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
        searchSourceBuilder.query(termQueryBuilder);
        //构建分页
        if (form == -1) {
            searchSourceBuilder.from();
        } else {
            searchSourceBuilder.from(form);
        }
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search.getHits().getHits();
    }

    /**
     * PC端模糊搜索用户。手机号，拼音，汉字参数必须有一个不为空
     *
     * @param index  索引
     * @param form   页数 如果查看第一页传-1
     * @param size   数量 默认传10
     * @param mobile 手机号 可以为null
     * @param pinYin 汉字拼音 可以为null
     * @param hanZi  汉字 可以为null
     * @return
     */
    public SearchHit[] queryLikeQuery(String index, Integer form, Integer size, String mobile, String pinYin, String hanZi) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        multi_match

        //判断是汉字搜索还是拼音搜索还是手机号搜索
        if (hanZi != null) {
            //QueryBuilders.multiMatchQuery 模糊查找
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(hanZi, "customerName");
            searchSourceBuilder.query(multiMatchQueryBuilder);
        }
        if (mobile != null) {
            char[] chars = mobile.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (char aChar : chars) {
                stringBuilder.append(aChar).append(" ");
            }
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(stringBuilder.toString(), "customerStrMobile");
            searchSourceBuilder.query(multiMatchQueryBuilder);
        }
        if (pinYin != null) {
            char[] chars = pinYin.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (char aChar : chars) {
                stringBuilder.append(aChar).append(" ");
            }
            String[] fieldNames = new String[2];
            fieldNames[0] = "customerNamePYAbbr";
            fieldNames[1] = "customerNamePYAll";
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(stringBuilder.toString(), fieldNames);
            searchSourceBuilder.query(multiMatchQueryBuilder);
        }
        //构建分页
        if (form == -1) {
            searchSourceBuilder.from();
        } else {
            searchSourceBuilder.from(form);
        }
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search.getHits().getHits();
    }
}
