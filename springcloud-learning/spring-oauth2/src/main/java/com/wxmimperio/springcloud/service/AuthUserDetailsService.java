package com.wxmimperio.springcloud.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        Map<String, AuthUser> authUsers = Maps.newHashMap();
        try {
            authUsers.put("wxm", new AuthUser("ADMIN", "wxm", "wxm"));
            authUsers.put("wxmimperio", new AuthUser("USER", "wxmimperio", "wxmimperio"));
            if (authUsers.containsKey(userName)) {
                userDetails = new User(userName, authUsers.get(userName).getPassWord(), true, true, true, true, getAuthorities(authUsers.get(userName)));
            } else {
                throw new RuntimeException(String.format("No auth for user %s", userName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(AuthUser authUser) {
        List<GrantedAuthority> authList = Lists.newArrayList();
        authList.add(new SimpleGrantedAuthority("ROLE_" + authUser.getRole()));
        return authList;
    }
}
