package com.wxmimperio.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxmimperio.http.util.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.protocol.HTTP.CONTENT_TYPE;

public class HttpMain {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClientUtil.getHttpClient();
        String url = "";
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("test", "");

        Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();

        Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
        String res = HttpClientUtil.doPost(
                url,
                gson2.toJson(paraMap).toString(),
                new Header[]{header},
                client
        );
        client.close();
    }
}
