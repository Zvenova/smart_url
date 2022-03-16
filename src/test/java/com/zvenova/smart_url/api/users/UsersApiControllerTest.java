package com.zvenova.smart_url.api.users;

import com.zvenova.smart_url.api.exception.request.FieldInRequestCannotBeEmpty;
import com.zvenova.smart_url.base.BaseApiTest;
import com.zvenova.smart_url.domain.entity.User;
import com.zvenova.smart_url.domain.security.Role;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        @Test
        @SneakyThrows
        public void testUserCreate_usernameEmpty() {

            User testUser = getTestUser();
            testUser.setUsername("");
            String content = new JSONObject()
                    .put("username", testUser.getUsername())
                    .put("password", testUser.getPassword())
                    .toString();

            mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof FieldInRequestCannotBeEmpty))
                    .andExpect(result -> assertEquals("Поле не может быть пустым!", result.getResolvedException().getMessage()));

            verify(userRepository, times(0)).save(testUser);
        }
    }

    private User getTestUser() {

        return User.builder().username("Олег").active(true).password("123456")
                .roles(Set.of(Role.USER)).build();
    }
}
