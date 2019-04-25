package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.bean.User;
import com.wxmimperio.spring.service.UserOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("cache")
public class UserOpsController {

    private UserOpsService userOpsService;

    @Autowired
    public UserOpsController(UserOpsService userOpsService) {
        this.userOpsService = userOpsService;
    }

    @PostMapping("save/saveUser")
    public User putUser(@RequestBody User user) {
        return userOpsService.putUserInCache(user);
    }

    @GetMapping("get/{userName}")
    public User getUserByName(@PathVariable String userName) {
        return userOpsService.getUserByName(userName);
    }

    @DeleteMapping("delete/{userName}")
    public void removeUserByName(@PathVariable String userName) {
        userOpsService.removeUserCacheByName(userName);
    }
}
