package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.bean.User;
import com.wxmimperio.spring.service.LoadCacheService;
import com.wxmimperio.spring.service.UserOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("cache")
public class UserOpsController {

    private UserOpsService userOpsService;
    private LoadCacheService loadCacheService;

    @Autowired
    public UserOpsController(UserOpsService userOpsService, LoadCacheService loadCacheService) {
        this.userOpsService = userOpsService;
        this.loadCacheService = loadCacheService;
    }

    @PostMapping("save/saveUser")
    public List<User> putUser(@RequestBody List<User> users) {
        return userOpsService.putUserInCache(users);
    }

    @GetMapping("get/{userName}")
    public User getUserByName(@PathVariable String userName) {
        return userOpsService.getUserByName(userName);
    }

    @DeleteMapping("delete/{userName}")
    public void removeUserByName(@PathVariable String userName) {
        userOpsService.removeUserCacheByName(userName);
    }

    @PostMapping("save/saveUserLoadingCache/{cacheKey}")
    public List<User> cacheUser(@PathVariable String cacheKey, @RequestBody List<User> users) {
        return loadCacheService.cacheUsers(cacheKey, users);
    }

    @GetMapping("get/userByAge/{cacheKey}/{age}")
    public List<User> getUsersByAge(@PathVariable String cacheKey, @PathVariable Integer age) {
        return loadCacheService.getUsersByAge(cacheKey, age);
    }

    @GetMapping("get/userByName/{cacheKey}/{userName}")
    public User getUsersByAge(@PathVariable String cacheKey, @PathVariable String userName) {
        return loadCacheService.getUserByName(cacheKey, userName);
    }
}
