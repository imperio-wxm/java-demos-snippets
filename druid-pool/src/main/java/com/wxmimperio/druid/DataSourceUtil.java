package com.wxmimperio.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by weiximing.imperio on 2017/4/17.
 */
public class DataSourceUtil {

    public static String confile = "druid";

    public DataSourceUtil() {
    }

    static Properties initProperties() {
        ResourceBundle bundle = ResourceBundle.getBundle(confile);
        Properties props = new Properties();
        for (String key : bundle.keySet()) {
            props.put(key, bundle.getString(key));
        }
        return props;
    }

    public static final DataSource getDataSource() throws Exception {
        return DruidDataSourceFactory.createDataSource(initProperties());
    }
}
