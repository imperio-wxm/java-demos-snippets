package com.wxmimperio.elasticsearch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.wxmimperio.elasticsearch.bean.ElasticSearchSearch;
import com.wxmimperio.elasticsearch.bean.EsQueryBoolType;
import com.wxmimperio.elasticsearch.common.CommonUtils;
import com.wxmimperio.elasticsearch.config.ElasticSearchConnector;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className ElasticSearchService.java
 * @description This is the description of ElasticSearchService.java
 * @createTime 2020-09-10 14:47:00
 */
@Service
public class ElasticSearchService {
    private final static Logger LOG = LoggerFactory.getLogger(ElasticSearchService.class);
    private final ElasticSearchConnector connector;
    private final int ES_REQUEST_TIME_OUT = 30;
    private final static int ES_REQUEST_MAX_SIZE = 10000;

    @Autowired
    public ElasticSearchService(ElasticSearchConnector connector) {
        this.connector = connector;
    }

    public boolean checkIndexExists(String indexName) throws Exception {
        StopWatch sw = new StopWatch();
        sw.start();
        IndicesExistsRequest existsRequest = new IndicesExistsRequest(indexName);
        ActionFuture<IndicesExistsResponse> existsResponse = connector.getClient().admin().indices().exists(existsRequest);
        boolean exists = existsResponse.actionGet(ES_REQUEST_TIME_OUT, TimeUnit.SECONDS).isExists();
        sw.stop();
        LOG.debug("Check index exists cost = {} ms", sw.getTotalTimeMillis());
        return exists;
    }

    public void bulkData(String indexName, String type, List<Map<String, Object>> bulkData) throws Exception {
        BulkRequestBuilder bulkRequest = connector.getClient().prepareBulk();
        for (Map<String, Object> data : bulkData) {
            bulkRequest.add(connector.getClient().prepareIndex(indexName, type).setSource(data));
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        LOG.info("Bulk to index = {} cost = {} ms", indexName, bulkResponse.getTookInMillis());
    }

    public List<JsonNode> searchData(ElasticSearchSearch search) throws Exception {
        StopWatch sw = new StopWatch();
        sw.start();
        if (!checkIndexExists(search.getIndexName())) {
            throw new RuntimeException(String.format("Index name = %s not exists.", search.getIndexName()));
        }
        if (search.getStartTime().after(search.getEndTime())) {
            throw new RuntimeException(String.format("The start time = %s needs to be less than the end time = %s", search.getStartTime(), search.getEndTime()));
        }
        SearchRequestBuilder requestBuilder = connector.getClient().prepareSearch(search.getIndexName());
        search.getQueryBuilders().add(ImmutableMap.of(EsQueryBoolType.MUST, buildBaseQuery(search.getStartTime(), search.getEndTime())));
        requestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setSize(ES_REQUEST_MAX_SIZE)
                .setQuery(buildQuery(search.getQueryBuilders()))
                .setExplain(true);
        SearchResponse searchResponse = requestBuilder.get(new TimeValue(ES_REQUEST_TIME_OUT, TimeUnit.SECONDS));
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<JsonNode> result = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            result.add(CommonUtils.parserMapToJson(hit.getSourceAsMap()));
        }
        sw.stop();
        LOG.info("Index = {}, startTime = {}, endTime = {}, search cost = {} ms, result size = {}",
                search.getIndexName(), search.getStartTime(), search.getEndTime(), sw.getTotalTimeMillis(), result.size());
        return result;
    }

    private QueryBuilder buildQuery(List<Map<EsQueryBoolType, QueryBuilder>> queryBuilders) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!CollectionUtils.isEmpty(queryBuilders)) {
            queryBuilders.forEach(queryBuilder -> queryBuilder.forEach((type, build) -> {
                switch (type) {
                    case MUST_NOT:
                        boolQuery.mustNot(build);
                        break;
                    case SHOULD:
                        boolQuery.should(build);
                        break;
                    case MUST:
                        boolQuery.must(build);
                        break;
                }
            }));
        }
        LOG.debug("Query builder = {}", boolQuery);
        return boolQuery;
    }

    private QueryBuilder buildBaseQuery(Timestamp startTime, Timestamp finishTime) {
        return QueryBuilders.rangeQuery("@timestamp")
                .from(startTime.getTime())
                .to(finishTime.getTime())
                .includeLower(true)
                .includeUpper(false);
    }
}
