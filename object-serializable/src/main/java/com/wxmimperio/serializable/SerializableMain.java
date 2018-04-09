package com.wxmimperio.serializable;

import com.wxmimperio.serializable.pojo.Person;

import java.io.*;

public class SerializableMain {

    public static void main(String[] args) throws Exception {
        Person person = new Person("wxm", 25);
        System.out.println(writeToStr(person));
        Person person1 = (Person)deserializeFromStr(writeToStr(person));
        System.out.println(person1.getNameAge());
    }

    public static String writeToStr(Object obj) throws IOException {
        // 此类实现了一个输出流，其中的数据被写入一个 byte 数组。
        // 缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray() 和 toString() 获取数据。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 专用于java对象序列化，将对象进行序列化
        ObjectOutputStream objectOutputStream = null;
        String serStr = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            objectOutputStream.close();
        }
        return serStr;
    }

    public static Object deserializeFromStr(String serStr) throws IOException {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            String deserStr = java.net.URLDecoder.decode(serStr, "ISO-8859-1");
            byteArrayInputStream = new ByteArrayInputStream(deserStr.getBytes("UTF-8"));
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            objectInputStream.close();
            byteArrayInputStream.close();
        }
        return null;
    }
}
