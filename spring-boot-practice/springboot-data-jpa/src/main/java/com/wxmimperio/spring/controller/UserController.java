package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.bean.User;
import com.wxmimperio.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/save")
    public User save(String name, Integer age, String gender) {
        return userRepository.save(new User(name, age, gender));
    }
}
