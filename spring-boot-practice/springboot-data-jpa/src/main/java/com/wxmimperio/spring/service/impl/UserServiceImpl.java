package com.wxmimperio.spring.service.impl;

import com.wxmimperio.spring.beans.User;
import com.wxmimperio.spring.repository.UserRepository;
import com.wxmimperio.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = {IllegalArgumentException.class})
    @Override
    public User saveUserWithRollBack(User user) {
        User u = userRepository.save(user);
        if (user.getName().equalsIgnoreCase("wxmimperio")) {
            throw new IllegalArgumentException("wxmimperio 已经存在，数据回滚");
        }
        return u;
    }

    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    @Override
    public User saveUserWithoutRollBack(User user) {
        User u = userRepository.save(user);
        if (user.getName().equalsIgnoreCase("wxmimperio")) {
            throw new IllegalArgumentException("wxmimperio 已经存在，不进行数据回滚");
        }
        return u;
    }
}
