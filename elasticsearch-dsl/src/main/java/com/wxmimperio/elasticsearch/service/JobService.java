package com.wxmimperio.elasticsearch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.wxmimperio.elasticsearch.bean.ElasticSearchSearch;
import com.wxmimperio.elasticsearch.bean.EsQueryBoolType;
import com.wxmimperio.elasticsearch.bean.IndexBase;
import com.wxmimperio.elasticsearch.bean.IndexRealTimeYarnConfig;
import com.wxmimperio.elasticsearch.common.CommonUtils;
import com.wxmimperio.elasticsearch.config.ElasticSearchConfig;
import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className JobService.java
 * @description This is the description of JobService.java
 * @createTime 2020-09-16 17:43:00
 */
@Service
public class JobService implements ApplicationRunner {

    private final ElasticSearchService elasticSearchService;
    private final ElasticSearchConfig elasticSearchConfig;

    @Autowired
    public JobService(ElasticSearchService elasticSearchService, ElasticSearchConfig elasticSearchConfig) {
        this.elasticSearchService = elasticSearchService;
        this.elasticSearchConfig = elasticSearchConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String applicationId = "application_1582801257255_74238";
        Timestamp startTime = new Timestamp(1600400369000L);
        List<JsonNode> nodes = elasticSearchService.searchData(buildRunningToFailingSearch(startTime, applicationId));
        List<IndexBase> indexBases = new ArrayList<>(nodes.size());
        for (JsonNode node : nodes) {
            indexBases.add(CommonUtils.parserJsonNodeToObj(node, IndexRealTimeYarnConfig.class));
        }

        System.out.println("=============");
        indexBases.forEach(index -> {
            System.out.println(index);
        });
        System.out.println("=============");
    }

    public List<IndexBase> search(Timestamp startTime, String applicationId) throws Exception {
        List<JsonNode> nodes = elasticSearchService.searchData(buildRunningToFailingSearch(startTime, applicationId));
        List<IndexBase> indexBases = new ArrayList<>(nodes.size());
        for (JsonNode node : nodes) {
            indexBases.add(CommonUtils.parserJsonNodeToObj(node, IndexRealTimeYarnConfig.class));
        }
        return indexBases;
    }


    private ElasticSearchSearch buildRunningToFailingSearch(Timestamp timestamp, String appId) {
        String indexName = elasticSearchConfig.getIndexName();
        Timestamp startTime = new Timestamp(timestamp.getTime() - 60000);

        System.out.println("start = " + startTime);
        System.out.println("end = " + timestamp);
        List<Map<EsQueryBoolType, QueryBuilder>> list = Lists.newArrayList();
        list.add(ImmutableMap.of(EsQueryBoolType.MUST, QueryBuilders.termQuery("applicationId", appId)));
        LinkedMultiValueMap<String, String> phraseMap = new LinkedMultiValueMap<>();
        buildPhraseMap(phraseMap);
        buildMatchPhraseQuery(list, phraseMap);
        return ElasticSearchSearch.ElasticSearchSearchBuilder.builder()
                .withIndexName(indexName)
                .withStartTime(startTime)
                .withEndTime(timestamp)
                .withQueryBuilders(list)
                .build();
    }

    private void buildPhraseMap(LinkedMultiValueMap<String, String> phraseMap) {
        phraseMap.add("log", "test");
        phraseMap.add("log", "ERROR");
    }

    private void buildMatchPhraseQuery(List<Map<EsQueryBoolType, QueryBuilder>> list, LinkedMultiValueMap<String, String> phraseMap) {
        phraseMap.forEach((param, value) -> {
            value.forEach(v -> {
                list.add(ImmutableMap.of(EsQueryBoolType.MUST, QueryBuilders.matchPhraseQuery(param, v)));
            });
        });
    }
}
