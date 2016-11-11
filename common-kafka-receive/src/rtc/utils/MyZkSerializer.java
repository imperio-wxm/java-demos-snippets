package com.sdo.dw.rtc.utils;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.nio.charset.Charset;

/**
 * Created by weiximing.imperio on 2016/11/11.
 */
public class MyZkSerializer implements ZkSerializer {
    @SuppressWarnings("Since15")
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes, Charset.forName("UTF-8"));
    }
    @SuppressWarnings("Since15")
    public byte[] serialize(Object obj) throws ZkMarshallingError {
        return String.valueOf(obj).getBytes(Charset.forName("UTF-8"));
    }
}