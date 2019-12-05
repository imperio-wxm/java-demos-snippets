package com.wxmimperio.spring.service;

import com.wxmimperio.spring.annotation.CalculateExeTime;
import org.springframework.stereotype.Service;

@Service
public class CalcutateExeTimeService {

    @CalculateExeTime
    public void exeTask(String time) {
        try {
            Thread.sleep(Long.parseLong(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
