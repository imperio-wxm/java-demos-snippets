package com.wxmimperio.serializable;

import com.wxmimperio.serializable.pojo.Person;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SerializableMain {

    private static String STREAM_CHARSET = "ISO-8859-1";
    private static String ENCODER_CHARSET = "UTF-8";

    public static void main(String[] args) throws Exception {
        Person personToStr = new Person("wxm", 25);
        String personStr = writeToStr(personToStr);
        System.out.println(personStr);
        Person strToPerson = (Person) deserializeFromStr(personStr);
        System.out.println(strToPerson.getNameAge());

        MySqlOps mySqlOps = new MySqlOps();
        String mysqlStr = writeToStr(mySqlOps);
        String sql = "select * from user";
        MySqlOps newMySqlOps = (MySqlOps) deserializeFromStr(mysqlStr);
        newMySqlOps.initConnection();
        newMySqlOps.selectSql(sql);
        newMySqlOps.close();
    }

    public static String writeToStr(Object obj) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return URLEncoder.encode(byteArrayOutputStream.toString(STREAM_CHARSET), ENCODER_CHARSET);
        }
    }

    public static Object deserializeFromStr(String serStr) throws IOException, ClassNotFoundException {
        String deserializeStr = URLDecoder.decode(serStr, ENCODER_CHARSET);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(deserializeStr.getBytes(STREAM_CHARSET));
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return objectInputStream.readObject();
        }
    }
}
