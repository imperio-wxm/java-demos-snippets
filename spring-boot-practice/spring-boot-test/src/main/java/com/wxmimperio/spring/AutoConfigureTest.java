package com.wxmimperio.spring;

import com.wxmimperio.spring.autoconfigure.service.AutoConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class AutoConfigureTest {

    private AutoConfigureService autoConfigureService;

    @Autowired
    public AutoConfigureTest(AutoConfigureService autoConfigureService) {
        this.autoConfigureService = autoConfigureService;
    }

    @RequestMapping("/")
    public String index() {
        return autoConfigureService.getUserAndPass();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoConfigureTest.class);
        MapAutoConfig mapAutoConfig = context.getBean(MapAutoConfig.class);
        System.out.println(mapAutoConfig.getRoutesPathPicker());
    }
}
