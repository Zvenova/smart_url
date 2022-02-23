package com.zvenova.like_my.api.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import org.json.JSONObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.zvenova.like_my.base.BaseApiTest;

import lombok.SneakyThrows;

import java.util.Set;


class UsersApiControllerTest extends BaseApiTest {

    @Nested
    public class TestCreateUser {

        @Test
        @SneakyThrows
        public void testUserCreate_Valid() {

            User testUser = getTestUser();
            String content = new JSONObject()
                    .put("username", testUser.getUsername())
                    .put("password", testUser.getPassword())
                    .toString();
            doReturn(testUser).when(userRepository).save(testUser);

            mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isOk());
            verify(userRepository, times(1)).save(testUser);
        }
    }

    private User getTestUser() {

        return User.builder().username("Олег").active(true).password("123456")
                .roles(Set.of(Role.USER)).build();
    }
}
