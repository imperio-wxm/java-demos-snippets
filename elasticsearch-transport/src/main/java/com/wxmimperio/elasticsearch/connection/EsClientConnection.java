package com.wxmimperio.elasticsearch.connection;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EsClientConnection {

    private Client client;

    public EsClientConnection() {
        initClient();
    }

    private void initClient() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            String esClusterIp = bundle.getString("es.cluster.ip");
            String path = this.getClass().getClassLoader().getResource("elastic-certificates.p12").getPath();
            path = path.substring(1, path.length());
            Settings settings = Settings.builder().put("client.transport.ignore_cluster_name", true)
                    .put("xpack.security.user", bundle.getString("es.cluster.username") + ":" + bundle.getString("es.cluster.password"))
                    .put("xpack.ssl.verification_mode", "certificate")
                    .put("xpack.ssl.keystore.path", path)
                    .put("xpack.ssl.truststore.path", path)
                    .put("xpack.security.transport.ssl.enabled", "true").build();
            List<TransportAddress> addresses = new ArrayList<>();
            for (String broker : esClusterIp.split(",")) {
                TransportAddress address = new TransportAddress(InetAddress.getByName(broker.split(":")[0]), Integer.parseInt(broker.split(":")[1]));
                addresses.add(address);
            }
            TransportClient tc = new PreBuiltXPackTransportClient(settings);
            client = tc.addTransportAddresses().addTransportAddresses(addresses.toArray(new TransportAddress[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return client;
    }
}
