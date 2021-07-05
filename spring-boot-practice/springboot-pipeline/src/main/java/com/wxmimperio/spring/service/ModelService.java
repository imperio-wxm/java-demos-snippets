package com.wxmimperio.spring.service;

import com.wxmimperio.spring.PipelineExecutor;
import com.wxmimperio.spring.context.InstanceBuildContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModelService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PipelineExecutor pipelineExecutor;


    @Autowired
    public ModelService(PipelineExecutor pipelineExecutor) {
        this.pipelineExecutor = pipelineExecutor;
    }

    public String buildModelInstance() {
        InstanceBuildContext data = createPipelineData();
        boolean success = pipelineExecutor.acceptSync(data);

        // boolean success = pipelineExecutor.acceptAsync(data);


        // 创建模型实例成功
        if (success) {
            return String.valueOf(data.getInstanceId());
        }


        logger.error("创建模式实例失败：{}", data.getErrorMsg());
        return data.getErrorMsg();
    }

    private InstanceBuildContext createPipelineData() {
        InstanceBuildContext context = new InstanceBuildContext();
        context.setErrorMsg("error");
        context.setInstanceId(123L);
        context.setUserId(456L);
        context.setEndTime(LocalDateTime.now());
        context.setStartTime(LocalDateTime.now());

        Map<String, Object> map = new HashMap<>();
        map.put("instanceName", "wxm_test");
        context.setFormInput(map);
        return context;
    }
}
