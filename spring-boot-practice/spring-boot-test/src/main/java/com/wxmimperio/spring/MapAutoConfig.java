package com.wxmimperio.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "zuul")
@Component
public class MapAutoConfig {

    private Map<String, MapConfig> routesPathPicker = new LinkedHashMap<>();

    public Map<String, MapConfig> getRoutesPathPicker() {
        return routesPathPicker;
    }

    public void setRoutesPathPicker(Map<String, MapConfig> routesPathPicker) {
        this.routesPathPicker = routesPathPicker;
    }

    private static class MapConfig {
        private String servicePath = "";
        private List<String> methods;

        public MapConfig() {
        }

        public String getServicePath() {
            return servicePath;
        }

        public void setServicePath(String servicePath) {
            this.servicePath = servicePath;
        }

        public List<String> getMethods() {
            return methods;
        }

        public void setMethods(List<String> methods) {
            this.methods = methods;
        }

        @Override
        public String toString() {
            return "MapConfig{" +
                    "servicePath='" + servicePath + '\'' +
                    ", methods=" + methods +
                    '}';
        }
    }
}
