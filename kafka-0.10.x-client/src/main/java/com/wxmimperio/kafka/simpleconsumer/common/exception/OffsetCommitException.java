package com.wxmimperio.kafka.simpleconsumer.common.exception;

/**
 * offset 提交异常类
 * Created by weiximing.imperio on 2016/9/18.
 */
public class OffsetCommitException extends RuntimeException {

    private String retCd;  //异常对应的返回码
    private String msgDes;  //异常对应的描述信息

    public OffsetCommitException() {
        super();
    }

    public OffsetCommitException(String message) {
        super(message);
        msgDes = message;
    }

    public OffsetCommitException(String retCd, String msgDes) {
        super();
        this.retCd = retCd;
        this.msgDes = msgDes;
    }

    public String getRetCd() {
        return retCd;
    }

    public String getMsgDes() {
        return msgDes;
    }
}
