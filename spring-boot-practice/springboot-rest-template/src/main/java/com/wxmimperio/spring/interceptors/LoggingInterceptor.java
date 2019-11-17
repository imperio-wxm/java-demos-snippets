package com.wxmimperio.spring.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * restTemplate 拦截器
 */
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Logger logger;

    public LoggingInterceptor() {
        this.logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // 自定义header
        HttpHeaders headers = request.getHeaders();
        headers.add("actionName", "wxmimperio");
        logger.info(String.format("Request: %s %s %s", request.getMethod(), request.getURI(), new String(body, getCharset(request))));
        ClientHttpResponse response = execution.execute(request, body);
        logger.info(String.format("Response: %s %s", response.getStatusCode().value(), StreamUtils.copyToString(response.getBody(), getCharset(response))));
        return response;
    }

    private static Charset getCharset(HttpMessage message) {
        return Optional.ofNullable(message.getHeaders().getContentType()).map(MimeType::getCharset).orElse(DEFAULT_CHARSET);
    }
}
