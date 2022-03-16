package com.zvenova.like_my.base;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.zvenova.like_my.domain.repository.UserRepository;
import com.zvenova.like_my.service.UserService;

@ActiveProfiles({ "test", "testWithDB" })
@SpringBootTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class BaseTestWithDB {

    @Autowired
    public UserRepository userRepository;

    @MockBean
    public UserService userService;
}
