package com.wxmimperio.spring.service.impl;

import com.wxmimperio.spring.beans.User;
import com.wxmimperio.spring.repository.UserRepository;
import com.wxmimperio.spring.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    private UserRepository userRepository;

    @Autowired
    public CacheServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @CachePut(value = "user", key = "#user.id")
    public User save(User user) {
        // 新增或更新到缓存
        User u = userRepository.save(user);
        System.out.println("id = " + user.getId() + " 缓存");
        return u;
    }

    @Override
    @CacheEvict(value = "user")
    public void remove(Integer id) {
        // 从缓存删除
        System.out.println("删除了 id = " + id);
    }

    @Override
    @Cacheable(value = "user", key = "#user.id")
    public User finOne(User user) {
        User u = userRepository.findOne(user.getId());
        System.out.println("id = " + user.getId() + " 缓存");
        return u;
    }
}
