package cn.izy;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author zouyu
 * @description  es 查询
 * @date 2020/4/21
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        //1.连接rest接口
        HttpHost http = new HttpHost("127.0.0.1", 9200, "http");
        RestClientBuilder builder = RestClient.builder(http);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);

        //2.构建查询条件
        SearchRequest sku = new SearchRequest("sku");
        sku.types("doc");
        //query
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //match
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "手机");
        searchSourceBuilder.query(matchQueryBuilder);
        sku.source(searchSourceBuilder);

        //开始查询
        SearchResponse searchResponse = restHighLevelClient.search(sku, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        long totalHits = searchHits.getTotalHits();
        System.out.println("总记录数"+totalHits);
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit : hits){
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
        //关闭rest
        restHighLevelClient.close();
    }
}
