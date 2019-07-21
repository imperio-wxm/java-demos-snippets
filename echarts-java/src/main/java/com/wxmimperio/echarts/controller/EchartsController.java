package com.wxmimperio.echarts.controller;

import com.wxmimperio.echarts.entity.Data;
import com.wxmimperio.echarts.entity.User;
import com.wxmimperio.echarts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.*;

@Controller
public class EchartsController {

    private DataService dataService;

    @Autowired
    public EchartsController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String show(Model model) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User(i, "张三" + i, Integer.toString(20 + i), "中国广州"));
        }
        model.addAttribute("users", userList);
        return "show";
    }

    @ResponseBody
    @RequestMapping(value = "getData", method = RequestMethod.GET)
    public List<List<Data>> getData() {
        dataService.get();
        List<List<Data>> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<Data> data = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                List<String> attr = new ArrayList<>();
                List<String> realData = new ArrayList<>();
                for (int k = 0; k < 1440; k++) {
                    attr.add("wxm" + k);
                    if (j == 0) {
                        realData.add(String.valueOf(k));
                    } else {
                        realData.add(String.valueOf(k * 2));
                    }
                }
                data.add(new Data("table_" + j, attr, realData));
            }
            result.add(data);
        }
        return result;
    }
}
