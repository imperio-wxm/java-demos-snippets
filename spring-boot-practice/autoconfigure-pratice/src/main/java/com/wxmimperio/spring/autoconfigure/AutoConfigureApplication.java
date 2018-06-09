package com.wxmimperio.spring.autoconfigure;


import com.wxmimperio.spring.autoconfigure.config.CustomizeConfig;
import com.wxmimperio.spring.autoconfigure.service.AutoConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CustomizeConfig.class)
@ConditionalOnClass(AutoConfigureService.class)
@ConditionalOnProperty(prefix = "auto", value = "enabled", matchIfMissing = true)
public class AutoConfigureApplication {

    private CustomizeConfig customizeConfig;

    @Autowired
    public AutoConfigureApplication(CustomizeConfig customizeConfig) {
        this.customizeConfig = customizeConfig;
    }

    @Bean
    @ConditionalOnMissingBean(AutoConfigureService.class)
    public AutoConfigureService autoConfigureService() {
        AutoConfigureService autoConfigureService = new AutoConfigureService();
        autoConfigureService.setUserName(customizeConfig.getUserName());
        autoConfigureService.setPassWord(customizeConfig.getPassWord());
        return autoConfigureService;
    }
}
