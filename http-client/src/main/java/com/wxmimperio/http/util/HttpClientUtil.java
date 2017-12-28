package com.wxmimperio.http.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
    // Log
    protected static Log log = LogFactory.getLog(HttpClientUtil.class);

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
                .setSocketTimeout(60 * 60 * 1000)
                .setConnectTimeout(60 * 60 * 1000)
                .setConnectionRequestTimeout(60 * 1000)
                .setExpectContinueEnabled(false).build();

    }

    public static CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    private static void validateStatus(HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status >= 300) {
            throw new IOException("Unexpected response status: " + status);
        }
    }

    public static void close(CloseableHttpClient client) {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String doPost(String url, String body, Header[] headers, CloseableHttpClient client)
            throws IOException {
        HttpEntity httpEntity = new StringEntity(body, Charset.forName("UTF-8"));
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(httpEntity);
        if (headers != null) {
            httpPost.setHeaders(headers);
        }
        CloseableHttpResponse response = client.execute(httpPost);
        try {
            validateStatus(response);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity, "utf-8");
        } finally {
            response.close();
        }
    }

    public static String doGet(String url, Map<String, String> param) throws IOException {
        CloseableHttpClient client = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        try {
            validateStatus(response);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } finally {
            response.close();
        }
    }

    public static String doGet(String url) throws Exception {
        String result = null;
        url = url.replaceAll(" ", "%20");
        try {
//			log.info("#request:"+url);
            HttpGet httpget = new HttpGet(url);
            HttpContext context = new BasicHttpContext();
            CloseableHttpResponse response = httpClient.execute(httpget, context);
            try {
                validateStatus(response);
                // get the response body as an array of bytes
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "utf-8");
//					log.info("#result:"+result);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 执行POST请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, Map<String, String> param) throws Exception {
        String result = null;

        log.info("-----temp-----url : " + url);
        HttpPost httpPost = new HttpPost(url);
        try {
//        	log.info("requestURL:"+url+","+param.toString());
            httpPost.setConfig(requestConfig);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (null != param) {
                for (Entry<String, String> entery : param.entrySet()) {
                    nvps.add(new BasicNameValuePair(entery.getKey(), String.valueOf(entery.getValue())));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            CloseableHttpResponse response = httpClient.execute(httpPost);

            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    try {
                        if (entity != null) {
                            result = EntityUtils.toString(entity);
                            log.info("#result:" + result);
//            				log.info("cost time:"+(System.currentTimeMillis()-start)+"ms,response:"+result);
                        }
                    } finally {
                        if (entity != null) {
                            entity.getContent().close();
                        }
                    }
                } else {
//            		log.info("HttpStatus:"+response.getStatusLine().getStatusCode());
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpPost.releaseConnection();
        }

        return result;
    }

    /**
     * 执行POST请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, JsonObject param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        String result = null;
        try {
            log.info("#请求地址：" + url + ";请求参数：" + param);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(param.toString(), HTTP.UTF_8));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    try {
                        if (entity != null) {
                            result = EntityUtils.toString(entity);
                            log.info("#result:" + result);
                        }
                    } finally {
                        if (entity != null) {
                            entity.getContent().close();
                        }
                    }
                } else {
                    log.info("HttpStatus:" + response.getStatusLine().getStatusCode());
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }

    /**
     * @param url
     * @param param
     * @return
     */
    public static String doPut(String url, JsonObject param) throws Exception {
        HttpPut httpPut = new HttpPut(url);
        String result = null;
        try {
            log.info("#请求地址：" + url + ";请求参数：" + param);
            httpPut.setConfig(requestConfig);
            if (param != null) {
                httpPut.setEntity(new StringEntity(param.toString(), HTTP.UTF_8));
            }
            CloseableHttpResponse response = httpClient.execute(httpPut);
            try {

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    try {
                        if (entity != null) {
                            result = EntityUtils.toString(entity);
                            log.info("#result:" + result);
                        }
                    } finally {
                        if (entity != null) {
                            entity.getContent().close();
                        }
                    }
                } else {
                    log.info("HttpStatus:" + response.getStatusLine().getStatusCode());
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            httpPut.releaseConnection();
        }
        return result;
    }

    public static String doDelete(String url, JsonObject param) throws Exception {
        HttpDelete httpDelete = new HttpDelete(url);
        String result = null;
        try {
            log.info("#请求地址：" + url + ";请求参数：" + param);
            httpDelete.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            try {

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    try {
                        if (entity != null) {
                            result = EntityUtils.toString(entity);
                            log.info("#result:" + result);
                        }
                    } finally {
                        if (entity != null) {
                            entity.getContent().close();
                        }
                    }
                } else {
                    log.info("HttpStatus:" + response.getStatusLine().getStatusCode());
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            httpDelete.releaseConnection();
        }
        return result;
    }


    public static String replaceBlank(String url) {
        url = StringUtils.replace(url, "|", "");
        url = StringUtils.strip(url);
        return url;
    }
}
