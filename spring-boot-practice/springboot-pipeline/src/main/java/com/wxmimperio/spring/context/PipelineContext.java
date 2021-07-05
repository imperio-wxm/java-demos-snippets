package com.wxmimperio.spring.context;

import java.time.LocalDateTime;

public class PipelineContext {


    /**
     * 处理开始时间
     */
    private LocalDateTime startTime;


    /**
     * 处理结束时间
     */
    private LocalDateTime endTime;


    /**
     * 获取数据名称
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
