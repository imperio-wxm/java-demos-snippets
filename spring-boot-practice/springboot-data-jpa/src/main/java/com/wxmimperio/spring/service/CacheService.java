package com.wxmimperio.spring.service;

import com.wxmimperio.spring.bean.User;

public interface CacheService {

    User save(User user);

    void remove(Integer id);

    User finOne(User user);
}
