package com.wxmimperio.spring.service;

import com.wxmimperio.spring.beans.User;

public interface UserService {
    User saveUserWithRollBack(User user);

    User saveUserWithoutRollBack(User user);
}
