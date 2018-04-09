package com.wxmimperio.serializable;

import com.wxmimperio.serializable.pojo.Person;

import java.io.*;

public class SerializableMain {

    private static String STREAM_CHARSET = "ISO-8859-1";
    private static String ENCODER_CHARSET = "UTF-8";

    public static void main(String[] args) throws Exception {
        Person person = new Person("wxm", 25);
        String personStr = writeToStr(person);
        System.out.println(personStr);
        Person person1 = (Person) deserializeFromStr(personStr);
        System.out.println(person1.getNameAge());
    }

    public static String writeToStr(Object obj) throws IOException {
        // 此类实现了一个输出流，其中的数据被写入一个 byte 数组。
        // 缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray() 和 toString() 获取数据。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 专用于java对象序列化，将对象进行序列化
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return java.net.URLEncoder.encode(byteArrayOutputStream.toString(STREAM_CHARSET), ENCODER_CHARSET);
        }
    }

    public static Object deserializeFromStr(String serStr) throws Exception {
        String deserStr = java.net.URLDecoder.decode(serStr, ENCODER_CHARSET);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(deserStr.getBytes(STREAM_CHARSET));
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return objectInputStream.readObject();
        }
    }
}
