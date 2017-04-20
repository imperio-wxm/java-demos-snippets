package com.wxmimperio.controller;

import com.wxmimperio.mapper.UserMapper;
import com.wxmimperio.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wxmimperio on 2017/4/21.
 */
@RestController
@RequestMapping("/demo")
@EnableAutoConfiguration
public class HelloController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/test")
    String test1() {
        return "hello,test1()";
    }

    @RequestMapping("/findByName")
    User findByName(@RequestParam String name) {
        return userMapper.findByName(name);
    }

    @RequestMapping("/insertUser")
    void insertUser(@RequestParam String name, @RequestParam Integer age, @RequestParam String gender) {
        userMapper.insert(name, age, gender);
    }
}
