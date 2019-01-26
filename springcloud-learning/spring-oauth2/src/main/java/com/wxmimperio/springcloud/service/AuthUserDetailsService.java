package com.wxmimperio.springcloud.service;

import com.google.common.collect.Lists;
import com.wxmimperio.springcloud.beans.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            AuthUser user = new AuthUser();
            user.setPassWord("wxm");
            user.setUserName("wxm");
            Collection<GrantedAuthority> authList = getAuthorities();
            userDetails = new User(userName, user.getPassWord(), true, true, true, true, authList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;

    }

    private Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = Lists.newArrayList();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }
}
