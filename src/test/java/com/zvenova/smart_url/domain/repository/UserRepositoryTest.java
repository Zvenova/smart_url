package com.zvenova.smart_url.domain.repository;

import com.zvenova.smart_url.base.BaseTestWithDB;
import com.zvenova.smart_url.domain.entity.User;
import com.zvenova.smart_url.domain.security.Role;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends BaseTestWithDB {

    @Test
    public void adminUserIsPresent() {

        User admin = userRepository.findByUsername("admin").orElseThrow(AssertionError::new);

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
                .roles(Set.of(Role.USER)).build();
    }
}
