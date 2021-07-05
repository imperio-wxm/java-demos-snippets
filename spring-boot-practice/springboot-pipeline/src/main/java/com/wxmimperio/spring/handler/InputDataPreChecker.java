package com.wxmimperio.spring.handler;


import com.wxmimperio.spring.context.InstanceBuildContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
public class InputDataPreChecker implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public InputDataPreChecker() {
    }

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--输入数据校验--");

        Map<String, Object> formInput = context.getFormInput();

        if (formInput == null || formInput.isEmpty()) {
            context.setErrorMsg("表单输入数据不能为空");
            return false;
        }

        String instanceName = (String) formInput.get("instanceName");
        if (StringUtils.isEmpty(instanceName)) {
            context.setErrorMsg("表单输入数据必须包含实例名称");
            return false;
        }
        return true;
    }
}
