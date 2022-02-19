package com.zvenova.like_my.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.zvenova.like_my.base.BaseTestWithDB;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;

class UserRepositoryTest extends BaseTestWithDB {

    @Test
    public void adminUserIsPresent() {

        User admin = userRepository.findByUsernameEquals("admin").orElseThrow(AssertionError::new);

        assertTrue(admin.isActive());

        assertEquals(2, admin.getRoles().size());
        assertTrue(admin.getRoles().contains(Role.USER));
        assertTrue(admin.getRoles().contains(Role.ADMIN));
    }

    @Test
    public void saveUserTest() {

        assertDoesNotThrow(() -> userRepository.save(getTestUser()),
                "UserRepository saving is not working!");

    }

    private User getTestUser() {

        return User.builder().username("Олег").active(true).password("123456")
                .roles(Collections.singleton(Role.USER)).build();
    }

}
