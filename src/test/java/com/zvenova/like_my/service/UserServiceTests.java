package com.zvenova.like_my.service;

import com.zvenova.like_my.base.BaseTestWithoutDB;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.mockito.Mockito.doReturn;

public class UserServiceTests extends BaseTestWithoutDB {

    @Test
    public void loadUserByUsername_Valid() {

        User testUser = getTestUser();
        doReturn(testUser).when(userService).loadUserByUsername("Олег");

        UserDetails user = userService.loadUserByUsername("Олег");
        System.out.println(user);
    }

    private User getTestUser() {
        return User.builder()
                .username("Олег")
                .id(1L)
                .active(true)
                .password("123456")
                .roles(Collections.singleton(Role.USER))
                .build();
    }
}
