package com.imperio.jetty.bean;

import com.imperio.jetty.exception.FlumeBaseException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JsonEvent {
    private Map<String, String> headers;
    private String body;
    private transient String charset = "UTF-8";

    public JsonEvent(String body, Map<String, String> headers) {
        this.headers = headers;
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() throws FlumeBaseException {
        if (body != null) {
            try {
                return body.getBytes(charset);
            } catch (UnsupportedEncodingException ex) {
                throw new FlumeBaseException(String.format("%s encoding not supported", charset), ex);
            }
        } else {
            return new byte[0];
        }
    }

    public void setBody(byte[] body) {
        if (body != null) {
            this.body = new String(body);
        } else {
            this.body = "";
        }
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
