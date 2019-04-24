package com.imperio.jetty.handler;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class FlumeHttpBaseHandler {
    private static final Logger LOG = LoggerFactory.getLogger(FlumeHttpBaseHandler.class);

    public static JSONObject jsonHandler(HttpServletRequest request) throws IOException {
        JSONObject jsonObject = new JSONObject();
        Map<String, String[]> parameters = request.getParameterMap();
        for (String parameter : parameters.keySet()) {
            String value = parameters.get(parameter)[0];
            jsonObject.put(parameter, value);
            LOG.info("Setting Header [Key, Value] as [{},{}] ", parameter, value);
        }
        return jsonObject;
    }
}
