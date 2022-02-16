package com.zvenova.like_my.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zvenova.like_my.base.BaseTestWithoutDB;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import com.zvenova.like_my.exception.UserIsAlreadyPresentException;

public class UserServiceTest extends BaseTestWithoutDB {

    @Autowired
    private UserService userService;

    @Nested
    public class TestFindUser {

        @Test
        public void whenUserNotPresent() {

            doReturn(Optional.empty()).when(userRepository).findByUsernameEquals("ADMIN");

            assertThrows(UsernameNotFoundException.class,
                    () -> userService.loadUserByUsername("ADMIN"));
        }

        @Test
        public void whenUserIsPresent() {

            User testUser = getTestUser();
            doReturn(Optional.of(testUser)).when(userRepository).findByUsernameEquals("ADMIN");

            User userFromService = userService.loadUserByUsername("ADMIN");

            assertEquals(testUser, userFromService);
        }
    }

    @Nested
    public class TestSaveUser {

        @Test
        public void whenUserNotPresent() {

            User testUser = getTestUser();
            doReturn(testUser).when(userRepository).save(testUser);

            assertDoesNotThrow(() -> userService.saveUser(testUser));
            verify(userRepository, times(1)).save(testUser);
        }

        @Test
        public void whenUserIsPresent() {

            User testUser = getTestUser();
            doReturn(Optional.of(testUser)).when(userRepository)
                    .findByUsernameEquals(testUser.getUsername());

            UserIsAlreadyPresentException exception = assertThrows(
                    UserIsAlreadyPresentException.class, () -> userService.saveUser(testUser));

            assertEquals(testUser.getUsername(), exception.getUsername());
            verify(userRepository, times(0)).save(testUser);
        }
    }

    private User getTestUser() {

        return User.builder().username("Олег").active(true).password("123456")
                .roles(Collections.singleton(Role.USER)).build();
    }
}
