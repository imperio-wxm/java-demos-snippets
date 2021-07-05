package com.wxmimperio.elasticsearch.bean;

import org.elasticsearch.index.query.QueryBuilder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className ElasticSearchSearch.java
 * @description This is the description of ElasticSearchSearch.java
 * @createTime 2020-09-10 19:34:00
 */
public class ElasticSearchSearch {
    private String indexName;
    private Timestamp startTime;
    private Timestamp endTime;
    private List<Map<EsQueryBoolType, QueryBuilder>> queryBuilders;

    public ElasticSearchSearch() {
    }


    public static final class ElasticSearchSearchBuilder {
        private String indexName;
        private Timestamp startTime;
        private Timestamp endTime;
        private List<Map<EsQueryBoolType, QueryBuilder>> queryBuilders;

        private ElasticSearchSearchBuilder() {
        }

        public static ElasticSearchSearchBuilder builder() {
            return new ElasticSearchSearchBuilder();
        }

        public ElasticSearchSearchBuilder withIndexName(String indexName) {
            this.indexName = indexName;
            return this;
        }

        public ElasticSearchSearchBuilder withStartTime(Timestamp startTime) {
            this.startTime = startTime;
            return this;
        }

        public ElasticSearchSearchBuilder withEndTime(Timestamp endTime) {
            this.endTime = endTime;
            return this;
        }

        public ElasticSearchSearchBuilder withQueryBuilders(List<Map<EsQueryBoolType, QueryBuilder>> queryBuilders) {
            this.queryBuilders = queryBuilders;
            return this;
        }

        public ElasticSearchSearch build() {
            ElasticSearchSearch elasticSearchSearch = new ElasticSearchSearch();
            elasticSearchSearch.queryBuilders = this.queryBuilders;
            elasticSearchSearch.endTime = this.endTime;
            elasticSearchSearch.indexName = this.indexName;
            elasticSearchSearch.startTime = this.startTime;
            return elasticSearchSearch;
        }
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public List<Map<EsQueryBoolType, QueryBuilder>> getQueryBuilders() {
        return queryBuilders;
    }

    public void setQueryBuilders(List<Map<EsQueryBoolType, QueryBuilder>> queryBuilders) {
        this.queryBuilders = queryBuilders;
    }
}
