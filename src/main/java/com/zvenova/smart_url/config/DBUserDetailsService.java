package com.zvenova.smart_url.config;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zvenova.smart_url.service.UserService;

import lombok.RequiredArgsConstructor;

@Lazy
@Service
@RequiredArgsConstructor
public class DBUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userService.findByUsername(username);
    }

}
