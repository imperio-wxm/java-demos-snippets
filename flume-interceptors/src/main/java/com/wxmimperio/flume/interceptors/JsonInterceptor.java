package com.wxmimperio.flume.interceptors;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxmimperio on 2017/3/26.
 */
public class JsonInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(JsonInterceptor.class);

    private final String header;
    private static final String HEADER_KEY = "topic";

    private JsonInterceptor(Context context) {
        this.header = context.getString(HEADER_KEY);
    }

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        return null;
    }

    @Override
    public List<Event> intercept(final List<Event> list) {
        List<Event> events = new ArrayList<>(list.size());
        try {
            for (Event event : list) {
                events.addAll(handler(event));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public void close() {

    }

    /**
     * message :
     * {"messageType":1,"name":"wxm","age":12}
     * {"messageType":2,"name":"imperio","age":20}
     * {"messageType":3,"name":"wxmimperio","age":25}
     *
     * @param event
     * @return
     * @throws Exception
     */
    private List<Event> handler(Event event) throws Exception {
        List<Event> events = new ArrayList<>();
        String message = new String(event.getBody(), "UTF-8");
        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();

        if (jsonObject.get("messageType").getAsInt() == 1) {
            event.getHeaders().put(HEADER_KEY, header);
            events.add(EventBuilder.withBody(event.getBody(), event.getHeaders()));
        } else {
            LOG.warn("Skip message = " + message);
        }
        return events;
    }
}
