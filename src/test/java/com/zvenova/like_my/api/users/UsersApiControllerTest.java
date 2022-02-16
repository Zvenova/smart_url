package com.zvenova.like_my.api.users;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.zvenova.like_my.base.BaseApiTest;

import lombok.SneakyThrows;

class UsersApiControllerTest extends BaseApiTest {

    @SneakyThrows
    @Test
    public void testUserCreate() {

        mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Cool Gadget\", \"description\": \"Looks cool\"}"))
                .andExpect(status().isOk());

    }

}
