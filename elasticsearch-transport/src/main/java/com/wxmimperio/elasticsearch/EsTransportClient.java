package com.wxmimperio.elasticsearch;

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
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class EsTransportClient {
    private static Client client;

    public static void main(String[] args) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String esClusterIp = bundle.getString("es.cluster.ip");
        ClassLoader classLoader = EsTransportClient.class.getClassLoader();
        String path = classLoader.getResource("elastic-certificates.p12").getPath();
        path = path.substring(1, path.length());
        Settings settings = Settings.builder().put("client.transport.ignore_cluster_name", true)
                .put("xpack.security.user", bundle.getString("es.cluster.username") + ":" + bundle.getString("es.cluster.password"))
                .put("xpack.ssl.verification_mode", "certificate")
                .put("xpack.ssl.keystore.path", path)
                .put("xpack.ssl.truststore.path", path)
                .put("xpack.security.transport.ssl.enabled", "true").build();


        List<TransportAddress> addresses = new ArrayList<>();
        for (String broker : esClusterIp.split(",")) {
            TransportAddress address = new TransportAddress(
                    InetAddress.getByName(broker.split(":")[0]), Integer.parseInt(broker.split(":")[1]));
            addresses.add(address);
        }

        TransportClient tc = new PreBuiltXPackTransportClient(settings);
        // do sth.
        client = tc.addTransportAddresses().addTransportAddresses(addresses.toArray(new TransportAddress[]{}));
        //createIndex("wxm_test_new_es01");
        System.out.println(indexExists("wxm_test_new_es01"));
        getIndexDetail("wxm_test_new_es01");
        //dropIndex("wxm_test_new_es01");
        System.out.println(indexExists("wxm_test_new_es01"));
        //updateIndex("wxm_test_new_es01");
        getIndexSchema("wxm_test_new_es01");
        updateSettings("wxm_test_new_es01");
    }


    public static void getIndexDetail(String indexName) {
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

    public static void createIndex(String indexName) {
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


    public static boolean indexExists(String indexName) {
        try {
            IndicesAdminClient adminClient = client.admin().indices();
            IndicesExistsResponse exists = adminClient.exists(new IndicesExistsRequest(indexName)).actionGet();
            return exists.isExists();
        } catch (IndexNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void dropIndex(String indexName) {
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

    public static void updateIndex(String indexName) {
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

    public static void getIndexSchema(String indexName) {
        try {
            IndicesAdminClient indicesAdminClient = client.admin().indices();
            GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest().indices(indexName)).get();
            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = getMappingsResponse.getMappings();
            Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> mappingIterator = mappings.iterator();
            while (mappingIterator.hasNext()) {
                ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>
                        objectObjectCursor = mappingIterator.next();
                ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
                ObjectLookupContainer<String> keys = immutableOpenMap.keys();
                Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
                while (keysIterator.hasNext()) {
                    String type = keysIterator.next().value;
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

    public static void updateSettings(String indexName) {
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
