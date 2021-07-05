package com.wxmimperio.xstream.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className Fetchers.java
 * @description This is the description of Fetchers.java
 * @createTime 2020-09-07 10:44:00
 */
@XStreamAlias("fetchers")
public class Fetchers {

    @XStreamImplicit
    private List<Fetcher> fetcherList;

    @XStreamAlias("fetcher")
    public static class Fetcher {
        @XStreamAlias("applicationtype")
        private String applicationType;
        @XStreamAlias("classname")
        private String className;
        @XStreamAlias("params")
        private Map<String, String> params;

        public Fetcher() {
        }

        public String getApplicationType() {
            return applicationType;
        }

        public void setApplicationType(String applicationType) {
            this.applicationType = applicationType;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

        @Override
        public String toString() {
            return "Fetcher{" +
                    "applicationType='" + applicationType + '\'' +
                    ", className='" + className + '\'' +
                    ", params=" + params +
                    '}';
        }
    }

    public List<Fetcher> getFetcherList() {
        return fetcherList;
    }

    public void setFetcherList(List<Fetcher> fetcherList) {
        this.fetcherList = fetcherList;
    }

    @Override
    public String toString() {
        return "Fetchers{" +
                "fetcherList=" + fetcherList +
                '}';
    }
}
