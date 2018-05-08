package com.wxmimperio.txt2sequencefile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SeqenceFileToPhoenix {

    public static void main(String[] args) {

        String inputPath = args[0];

        SeqenceFileToPhoenix seqenceFileToPhoenix = new SeqenceFileToPhoenix();
        seqenceFileToPhoenix.run(inputPath);
    }

    private void run(String inputPath) {
        String driver = "org.apache.phoenix.jdbc.PhoenixDriver";
        String url = "jdbc:phoenix:10.128.113.130,10.128.113.135,10.128.113.137:2181";

        InputStream input = this.getClass().getResourceAsStream("/phoenix-site.properties");
        Properties props = new Properties();
        try {
            props.load(input);
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        //String insertSql = "UPSERT INTO PHOENIX_APOLLO.CRM_VIP2_LEV_MID VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertSql = "UPSERT INTO PHOENIX_APOLLO.swy_character_login_glog VALUES(?,TO_TIMESTAMP(?,'yyyy-MM-dd HH:mm:ss','GMT+8'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        HDFSRunner.sequenceFileWritePhoenix(inputPath, url, props, insertSql);
    }
}
