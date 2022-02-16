package com.zvenova.like_my.config;

import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.zvenova.like_my.base.BaseTestWithDB;
import com.zvenova.like_my.base.BaseTestWithoutDB;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;

public class DBUserDetailsServiceTests extends BaseTestWithoutDB {

    @Autowired
    private DBUserDetailsService dbUserDetailsService;

    @Test
    // todo mock user service and write tests
    public void loadUserByUsername_Valid() {

        User insertedUser = userRepository.save(getTestUser());

        UserDetails resultUser = dbUserDetailsService.loadUserByUsername(insertedUser.getUsername());

        Assertions.assertThat(resultUser.getUsername()).isEqualTo(insertedUser.getUsername());
    }

    private User getTestUser() {

        return User.builder().username("Олег").active(true).password("123456")
                .roles(Collections.singleton(Role.USER)).build();
    }
}
