package com.sdo.dw.rtc.utils;

import org.I0Itec.zkclient.exception.ZkMarshallingError;

/**
 * Created by weiximing.imperio on 2016/11/11.
 */
public interface ZkSerializer {

    //序列化，将对象转为字节码
    public byte[] serialize(Object data) throws ZkMarshallingError;

    //反序列化，将字节码转为对象
    public Object deserialize(byte[] bytes) throws ZkMarshallingError;
}