package com.zvenova.like_my.service;

import com.zvenova.like_my.base.BaseTestWithoutDB;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import com.zvenova.like_my.api.exception.user.UserDoesNotExistsException;
import com.zvenova.like_my.api.exception.user.UserIsAlreadyPresentException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest extends BaseTestWithoutDB {

    @Autowired
    private UserService userService;

    @Nested
    public class TestFindUserByUsername {

        @Test
        public void whenUserNotPresent() {

            doReturn(Optional.empty()).when(userRepository).findByUsername("ADMIN");

            assertThrows(UsernameNotFoundException.class,
                    () -> userService.findByUsername("ADMIN"));
        }

        @Test
        public void whenUserIsPresent() {

            User testUser = getTestUser();
            doReturn(Optional.of(testUser)).when(userRepository).findByUsername("ADMIN");

            User userFromService = userService.findByUsername("ADMIN");

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
                    .findByUsername(testUser.getUsername());

            UserIsAlreadyPresentException exception = assertThrows(
                    UserIsAlreadyPresentException.class, () -> userService.saveUser(testUser));

            assertEquals(testUser.getUsername(), exception.getUsername());
            verify(userRepository, times(0)).save(testUser);
        }
    }

    @Nested
    public class TestDeleteUser {

        @Test
        public void whenUserIsPresent() {

            User testUser = getTestUser();
            doNothing().when(userRepository).deleteById(testUser.getId());
            doReturn(true).when(userRepository).existsById(testUser.getId());

            assertDoesNotThrow(() -> userService.deleteUser(testUser));
            verify(userRepository, times(1)).deleteById(testUser.getId());
            verify(userRepository, times(1)).existsById(testUser.getId());
        }

        @Test
        public void whenUserNotPresent() {

            User testUser = getTestUser();
            doReturn(false).when(userRepository).existsById(testUser.getId());

            assertThrows(UserDoesNotExistsException.class, () -> userService.deleteUser(testUser));
            verify(userRepository, times(0)).deleteById(testUser.getId());
            verify(userRepository, times(1)).existsById(testUser.getId());
        }
    }

    @Nested
    public class TestFindUserById {

        @Test
        public void whenUserIsPresent() throws UserDoesNotExistsException {

            User testUser = getTestUser();
            doReturn(Optional.of(testUser)).when(userRepository).findById(testUser.getId());

            User userFromDB = userService.findById(testUser.getId());
            assertEquals(testUser, userFromDB);
            assertDoesNotThrow(() -> userService.findById(testUser.getId()));
            verify(userRepository, times(2)).findById(testUser.getId());
        }

        @Test
        public void whenUserNotPresent() {

            User testUser = getTestUser();
            doReturn(Optional.empty()).when(userRepository).findById(testUser.getId());

            assertThrows(UserDoesNotExistsException.class, () -> userService.findById(testUser.getId()));
            verify(userRepository, times(1)).findById(testUser.getId());
        }
    }

    private User getTestUser() {

        return User.builder().id(2L).username("Олег").active(true).password("123456")
                .roles(Set.of(Role.USER)).build();
    }
}
