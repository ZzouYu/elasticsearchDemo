package cn.izy;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author zouyu
 * @description 筛选品牌为小米的记录
 * @date 2020/4/21
 */
public class Test4 {
    public static void main(String[] args) throws IOException {
        HttpHost http = new HttpHost("127.0.0.1", 9200, "http");
        RestClientBuilder builder = RestClient.builder(http);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        SearchRequest sku = new SearchRequest("sku");
        sku.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("brandName", "小米");
        boolQueryBuilder.filter(matchQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
         sku.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(sku, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        long totalHits = searchHits.getTotalHits();
        System.out.println("总记录数"+totalHits);
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit : hits){
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
        restHighLevelClient.close();
    }
}
