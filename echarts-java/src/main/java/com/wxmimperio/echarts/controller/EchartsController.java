package com.wxmimperio.echarts.controller;

import com.wxmimperio.echarts.entity.User;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class EchartsController {

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
    public List<Integer> getData() {
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        data.add(6);
        return data;
    }
}
