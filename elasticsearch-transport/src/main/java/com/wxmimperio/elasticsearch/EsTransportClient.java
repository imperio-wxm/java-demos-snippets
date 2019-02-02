package com.wxmimperio.elasticsearch;

import com.wxmimperio.elasticsearch.connection.EsClientConnection;
import com.wxmimperio.elasticsearch.service.EsOps;

import org.elasticsearch.client.Client;

public class EsTransportClient {

    public static void main(String[] args) throws Exception {
        EsClientConnection esClientConnection = new EsClientConnection();
        String indexName = "swy_dev_switch_newswitch_20180828";
        try (Client client = esClientConnection.getClient()) {
            new EsTransportClient().esClientMain(client, indexName);
        }
    }

    private void esClientMain(Client client, String indexName) throws Exception {
        //EsOps.getIndexDetail(client, indexName);

        //EsOps.getMappingsResponse(client,indexName);

        //EsOps.getIndex(client, indexName);

        //EsOps.getAliases(client, indexName);

        //EsOps.getSettingsResponse(client,indexName);

        //EsOps.getFieldMappings(client, indexName);

        EsOps.getTemplates(client, indexName);
    }
}
