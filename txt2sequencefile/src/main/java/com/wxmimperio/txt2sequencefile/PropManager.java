package com.wxmimperio.txt2sequencefile;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/9/26.
 */
public class PropManager {
    private static final Logger LOG = LoggerFactory.getLogger(PropManager.class);
    private static CompositeConfiguration config;

    private static class SingletonHolder {
        private static final PropManager INSTANCE = new PropManager();
    }

    public static PropManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private PropManager() {
        try {
            String propFile = System.getProperty("user.dir") + "/config/application.properties";
            config = new CompositeConfiguration();
            config.addConfiguration(new PropertiesConfiguration(propFile));
            LOG.info("init properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
            LOG.error("init properties happen error ", e);
        }
    }

    public String getPropertyByString(String key) {
        return config.getString(key);
    }

    public String[] getPropertyByStringArray(String key) {
        return config.getStringArray(key);
    }

    public String getPropertyByComma(String key) {
        return StringUtils.join(this.getPropertyByStringArray(key), ",");
    }

    public List<String> getPropertyByStringList(String key) {
        List<String> list = new ArrayList<String>();
        for (Object obj : this.getPropertyByObjList(key)) {
            list.add((String) obj);
        }
        return list;
    }

    public List<Object> getPropertyByObjList(String key) {
        return config.getList(key);
    }
}
