package com.example.ajie_es.service;


import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<Map<String,Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {
        if (pageNo<=1){
            pageNo = 1;
        }
        //条件搜索
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchRequest searchRequest = new SearchRequest("sentence");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(pageNo-1);
        sourceBuilder.size(pageSize);
        //精确值查询
//        FuzzyQueryBuilder describe = QueryBuilders.fuzzyQuery("describe", keyword);
//        TermQueryBuilder describe = QueryBuilders.termQuery("describe",keyword);
//        QueryStringQueryBuilder describe = new QueryStringQueryBuilder("\""+keyword+"\"");
//        SimpleQueryStringBuilder describe = new SimpleQueryStringBuilder(keyword);
        MatchQueryBuilder describe = QueryBuilders.matchQuery("describe",keyword);
        boolBuilder.must(describe);
        sourceBuilder.query(boolBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

//        System.out.println("searchResponse = " + searchResponse);

        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
//            System.out.println("documentFields = " + documentFields);
            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }




}

