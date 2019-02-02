package com.wxmimperio.elasticsearch.service;

import com.carrotsearch.hppc.ObjectLookupContainer;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.IndexNotFoundException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class EsOps {

    public static void getMappingsResponse(Client client, String indexName) throws Exception {
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest().indices(indexName)).get();
        ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappingMetaDataMap = getMappingsResponse.getMappings();
        Iterator<ImmutableOpenMap<String, MappingMetaData>> mapIterator = mappingMetaDataMap.valuesIt();
        while (mapIterator.hasNext()) {
            ImmutableOpenMap<String, MappingMetaData> metaDataMap = mapIterator.next();
            Iterator<MappingMetaData> metaData = metaDataMap.valuesIt();
            while (metaData.hasNext()) {
                MappingMetaData mappingMetaData = metaData.next();
                Map<String, Object> sourceMap = mappingMetaData.getSourceAsMap();
                sourceMap.forEach((key, value) -> {
                    /*key = _all, value = {enabled=false}
                    key = properties,*/
                    System.out.println(String.format("key = %s, value = %s", key, value));
                });
            }
        }
    }

    public static void getIndex(Client client, String indexName) throws Exception {
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        GetIndexResponse getIndexResponse = indicesAdminClient.getIndex(new GetIndexRequest().indices(indexName)).get();
        // indices
        String[] indices = getIndexResponse.getIndices();
        System.out.println("indices = " + Arrays.asList(indices));

        List<String> aliasLists = new ArrayList<>();
        List<String> indexRoutingLists = new ArrayList<>();
        List<String> searchRoutingLists = new ArrayList<>();

        // aliases
        ImmutableOpenMap<String, List<AliasMetaData>> aliases = getIndexResponse.getAliases();
        Iterator<List<AliasMetaData>> aliasesIterator = aliases.valuesIt();
        while (aliasesIterator.hasNext()) {
            List<AliasMetaData> aliasMetaDataList = aliasesIterator.next();
            aliasMetaDataList.forEach(aliasMetaData -> {
                aliasLists.add(aliasMetaData.getAlias());
                indexRoutingLists.add(aliasMetaData.getIndexRouting());
                searchRoutingLists.add(aliasMetaData.getSearchRouting());
            });
        }
        System.out.println(String.format("aliasLists = %s", aliasLists));
        System.out.println(String.format("indexRoutingLists = %s", indexRoutingLists));
        System.out.println(String.format("searchRoutingLists = %s", searchRoutingLists));
    }

    public static void getIndexDetail(Client client, String indexName) {
        try {
            IndicesAdminClient indicesAdminClient = client.admin().indices();
            GetSettingsResponse getSettingsResponse = indicesAdminClient.getSettings(new GetSettingsRequest().indices(indexName)).get();
            ImmutableOpenMap<String, Settings> settings = getSettingsResponse.getIndexToSettings();
            for (ObjectObjectCursor<String, Settings> objectObjectCursor : settings) {
                Settings setting = objectObjectCursor.value;
                for (String key : setting.keySet()) {
                    System.out.println(key + " = " + setting.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createIndex(Client client, String indexName) {
        try {
            Integer replicas = 1;
            Integer shards = 3;
            String aliasNameForRead = indexName;
            String aliasNameForWrite = indexName + "$";
            String realIndexName = aliasNameForRead + "-" + new SimpleDateFormat("yyMMdd").format(new Date());
            IndicesAdminClient adminClient = client.admin().indices();
            StringBuilder settings = new StringBuilder();
            settings.append("{ ");
            settings.append("\"index.refresh_interval\":\"5s\", ");
            settings.append("\"index.number_of_replicas\":" + replicas + ", ");
            settings.append("\"index.number_of_shards\":" + shards + "");
            settings.append("}");

            // { "index.refresh_interval":"5s", "index.number_of_replicas":2, "index.number_of_shards":5}, mappings = {"data":{"_all":{"enabled":false},"properties":{"col1":{"type":"keyword"},"col2":{"type":"keyword"},"col3":{"type":"text"}}}}
            String mappings = "{\"data\":{\"_all\":{\"enabled\":false},\"properties\":{\"col1\":{\"type\":\"keyword\"},\"col2\":{\"type\":\"keyword\"},\"col3\":{\"type\":\"text\"}}}}";
            CreateIndexResponse created = adminClient
                    .create(new CreateIndexRequest(realIndexName).settings(settings.toString(), XContentType.JSON)
                            .mapping("data", mappings, XContentType.JSON))
                    .actionGet();
            if (!created.isAcknowledged()) {
            }
            IndicesAliasesResponse aliasesRes = adminClient.aliases(new IndicesAliasesRequest()
                    .addAliasAction(IndicesAliasesRequest.AliasActions.add().index(realIndexName).alias(aliasNameForRead))
                    .addAliasAction(IndicesAliasesRequest.AliasActions.add().index(realIndexName).alias(aliasNameForWrite))).actionGet();
            if (!aliasesRes.isAcknowledged()) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean indexExists(Client client, String indexName) {
        try {
            IndicesAdminClient adminClient = client.admin().indices();
            IndicesExistsResponse exists = adminClient.exists(new IndicesExistsRequest(indexName)).actionGet();
            return exists.isExists();
        } catch (IndexNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void dropIndex(Client client, String indexName) {
        try {
            String[] realNames = client.admin().indices().getIndex(new GetIndexRequest().indices(indexName)).actionGet().getIndices();
            Arrays.asList(realNames).forEach(realName -> {
                DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(realName).execute().actionGet();
                if (!dResponse.isAcknowledged()) {
                    throw new RuntimeException();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateIndex(Client client, String indexName) {
        try {
            String mappings = "{\n" +
                    "    \"data\": {\n" +
                    "        \"_all\": {\n" +
                    "            \"enabled\": false\n" +
                    "        },\n" +
                    "        \"properties\": {\n" +
                    "            \"col1\": {\n" +
                    "                \"type\": \"keyword\"\n" +
                    "            },\n" +
                    "            \"col2\": {\n" +
                    "                \"type\": \"keyword\"\n" +
                    "            },\n" +
                    "            \"col3\": {\n" +
                    "                \"type\": \"text\"\n" +
                    "            },\n" +
                    "            \"col4\": {\n" +
                    "                \"type\": \"keyword\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            client.admin().indices().preparePutMapping(indexName).setType("data").setSource(mappings, XContentType.JSON).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getIndexSchema(Client client, String indexName) {
        try {
            IndicesAdminClient indicesAdminClient = client.admin().indices();
            GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest().indices(indexName)).get();
            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = getMappingsResponse.getMappings();
            for (ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>> objectObjectCursor : mappings) {
                ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
                ObjectLookupContainer<String> keys = immutableOpenMap.keys();
                for (ObjectCursor<String> key : keys) {
                    String type = key.value;
                    MappingMetaData mappingMetaData = immutableOpenMap.get(type);
                    Map<String, Object> mapping = mappingMetaData.getSourceAsMap();
                    if (mapping.containsKey("properties")) {
                        Map<String, Object> properties = (Map<String, Object>) mapping.get("properties");
                        for (String attribute : properties.keySet()) {
                            System.out.println("attribute = " + attribute + ", value = " + properties.get(attribute).toString());
                        }
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void updateSettings(Client client, String indexName) {
        try {
            StringBuilder settings = new StringBuilder();
            settings.append("{ ");
            settings.append("\"index.refresh_interval\":\"10s\", ");
            settings.append("\"index.number_of_replicas\":" + 1 + "");
            //settings.append("\"index.number_of_shards\":" + 2 + "");
            settings.append("}");
            client.admin().indices().prepareUpdateSettings(indexName).setSettings(settings.toString(), XContentType.JSON).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
