package com.wxmimperio.kafka.simpleconsumer.common;

/**
 * Created by weiximing.imperio on 2016/9/19.
 */
public class CommitOffset {
    private long offset;

    private long now;

    public CommitOffset() {
    }
    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }
}
