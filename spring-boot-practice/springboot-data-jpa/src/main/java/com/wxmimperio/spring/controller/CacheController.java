package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.beans.User;
import com.wxmimperio.spring.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    private CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("save")
    public User save(@RequestBody User user) {
        return cacheService.save(user);
    }

    @GetMapping("findOne/{id}")
    public User findOne(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        return cacheService.finOne(user);
    }

    @DeleteMapping("delete")
    public void delete(Integer id) {
        cacheService.remove(id);
    }
}
