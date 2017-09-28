package com.wxmimperio.es;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	
	private static Log logger = LogFactory.getLog(HttpClientUtil.class);
	
	private static CloseableHttpClient httpClient;
	private static RequestConfig requestConfig;
	static {
		 PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
         MessageConstraints messageConstraints = MessageConstraints.custom()
														                 .setMaxHeaderCount(200)
														                 .setMaxLineLength(2000)
														                 .build();
	        ConnectionConfig connectionConfig = ConnectionConfig.custom()
														                .setMalformedInputAction(CodingErrorAction.IGNORE)
														                .setUnmappableInputAction(CodingErrorAction.IGNORE)
														                .setCharset(Consts.UTF_8)
														                .setMessageConstraints(messageConstraints)
														                .build();
	            connManager.setDefaultConnectionConfig(connectionConfig);
	            connManager.setMaxTotal(200);
	            connManager.setDefaultMaxPerRoute(20);
	     httpClient = HttpClients.custom().setConnectionManager(connManager).build();
	     requestConfig = RequestConfig.custom()
                 .setSocketTimeout(60*60*1000)
                 .setConnectTimeout(60*60*1000)
                 .setConnectionRequestTimeout(60*1000)
                 .setExpectContinueEnabled(false).build();
	     
	}
	
	public static String doGet(String url) throws IOException, InterruptedException{
		String result = null;
		HttpGet httpget = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		CloseableHttpResponse response = httpClient.execute(httpget,context);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity,"utf-8");
		}
		return result;
	}
	
}
