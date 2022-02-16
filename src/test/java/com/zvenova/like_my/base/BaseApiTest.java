package com.zvenova.like_my.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class BaseApiTest extends BaseTestWithoutDB {

    @Autowired
    public MockMvc mockMvc;

}
