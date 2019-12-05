package com.wxmimperio.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxmimperio.spring.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestApplication implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApplication.class);
    private static final String RETURN_CODE = "returnCode";
    private static ConfigurableApplicationContext context;
    private RestTemplate restTemplate;

    public RestApplication() {
        this.restTemplate = new RestTemplate();
    }

    public static void main(String[] args) {
        context = SpringApplication.run(RestApplication.class, args);
        RestService restService = context.getBean(RestService.class);
        System.out.println(restService.get("http://10.1.10.196:19700/manage/clusters?operatorId=1"));
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        RestApplication restApplication = new RestApplication();
       /* JSONObject request = JSON.parseObject("");
        JSONObject result = restApplication.post("streamJob/addJob", request);
        System.out.println(result);*/
    }

    public JSONObject post(String path, JSONObject params) {
        return execute(path, params, (url) -> {
            try {
                return restTemplate.postForObject(url, null, String.class, params);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }, HttpMethod.POST);
    }

    private JSONObject execute(String path, JSONObject params, ExecuteFuction function, HttpMethod method) {
        String url = getUrl(path, params);
        LOGGER.info("Execute {}, url = {}, params = {}", method.name(), url, params);
        JSONObject result = JSON.parseObject(function.execute(url));
        System.out.println(result);
        if (null != result && result.getInteger(RETURN_CODE) != 0) {
            LOGGER.error(String.format("Failed to %s, url = %s, error = %s", method.name(), url,
                    result.getString("returnMessage")));
        }
        return result;
    }

    private String getUrl(String path, JSONObject params) {
        StringBuilder url = new StringBuilder();
        url.append(getRtManagerUrl()).append(path).append(processParams(params));
        return url.toString();
    }

    private String getRtManagerUrl() {
        return "";
    }

    private String processParams(JSONObject params) {
        StringBuilder sb = new StringBuilder();
        if (params.isEmpty()) {
            return sb.toString();
        }
        sb.append("?");
        params.forEach((key, value) -> {
            sb.append(key).append("={").append(key).append("}&");
        });
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

@FunctionalInterface
interface ExecuteFuction {
    String execute(String url);
}
