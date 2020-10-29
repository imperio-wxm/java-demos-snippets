package com.wxmimperio.elasticsearch.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className CommonUtils.java
 * @description This is the description of CommonUtils.java
 * @createTime 2020-09-07 10:48:00
 */
public class CommonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static JsonNode parserMapToJson(Map<?, ?> jsonMap) throws JsonProcessingException {
        return objectMapper.convertValue(jsonMap, JsonNode.class);
    }

    public static <T> T parserJsonNodeToObj(JsonNode srcJson, Class<T> targetClass) throws JsonProcessingException {
        return objectMapper.treeToValue(srcJson, targetClass);
    }
}

