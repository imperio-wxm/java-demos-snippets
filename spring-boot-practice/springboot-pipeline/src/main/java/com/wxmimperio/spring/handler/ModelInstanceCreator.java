package com.wxmimperio.spring.handler;

import com.wxmimperio.spring.context.InstanceBuildContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ModelInstanceCreator implements ContextHandler<InstanceBuildContext> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--根据输入数据创建模型实例--");


        // 假装创建模型实例


        return true;
    }
}
