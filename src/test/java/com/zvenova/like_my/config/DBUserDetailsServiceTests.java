package com.zvenova.like_my.config;

import java.util.Collections;
import java.util.Optional;

import com.zvenova.like_my.exception.user.UserIsAlreadyPresentException;
import com.zvenova.like_my.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.zvenova.like_my.base.BaseTestWithoutDB;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DBUserDetailsServiceTests extends BaseTestWithoutDB {

    @Autowired
    private DBUserDetailsService dbUserDetailsService;

    @MockBean
    @Autowired
    private UserService userService;

    @Nested
    public class TestLoadUserByUsername {

        @Test
        public void loadUserByUsernameIsPresent() throws UserIsAlreadyPresentException {

            User testUser = getTestUser();

            doReturn(testUser).when(userService).saveUser(testUser);
            doReturn(testUser).when(userService).findByUsername(testUser.getUsername());

            User insertedUser = userService.saveUser(testUser);

            UserDetails resultUser = dbUserDetailsService.loadUserByUsername(insertedUser.getUsername());

            Assertions.assertThat(resultUser.getUsername()).isEqualTo(insertedUser.getUsername());
            verify(userService, times(1)).saveUser(testUser);
            verify(userService, times(1)).findByUsername(testUser.getUsername());
        }

        @Test
        public void loadUserByUsernameNotPresent() {

            User testUser = getTestUser();

            doThrow(new UsernameNotFoundException(testUser.getUsername()))
                    .when(userService).findByUsername(testUser.getUsername());

            assertThrows(UsernameNotFoundException.class,
                    () -> dbUserDetailsService.loadUserByUsername(testUser.getUsername()));
            verify(userService, times(1)).findByUsername(testUser.getUsername());
        }
    }

    private User getTestUser() {

        return User.builder().username("Олег").active(true).password("123456")
                .roles(Collections.singleton(Role.USER)).build();
    }
}
