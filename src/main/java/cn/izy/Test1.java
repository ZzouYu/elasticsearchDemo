package cn.izy;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zouyu
 * @description
 * @date 2020/4/21
 */
public class Test1 {
    public static void main(String[] args) throws IOException {
        //1.连接rest接口
        HttpHost httpHost = new HttpHost("127.0.0.1", 9200,"http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);

        //封装请求对象
        BulkRequest bulkRequest = new BulkRequest();
        IndexRequest indexRequest=new IndexRequest("sku","doc","3");
        Map skuMap = new HashMap();
        skuMap.put("name","华为mate 20 pro");
        skuMap.put("brandName","华为");
        skuMap.put("categoryName","手机");
        skuMap.put("price",1010221);
        skuMap.put("createTime","2019-06-01");
        skuMap.put("saleNum",11021);
        skuMap.put("commentNum",102321);
        Map spec=new HashMap();
        spec.put("网络制式","移动4G");
        spec.put("屏幕尺寸","5");
        skuMap.put("spec",spec);
        indexRequest.source(skuMap);


        bulkRequest.add(indexRequest);
    //3.获取响应结果
      //IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        int status = bulk.status().getStatus();
      System.out.println(status);
        restHighLevelClient.close();
    }
}
