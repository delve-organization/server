package com.github.delve.integrationtest.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.dev.UserTestData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import com.github.delve.security.dto.EditUserRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.delve.dev.UserTestData.ADMIN_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    public void editUser() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        final EditUserRequest request = new EditUserRequest();
        request.setName("nimdA");

        mvc.perform(post("/api/user/edit")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    public void getUser() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/user/get-user")
                .param("userId", ADMIN_ID.toString())
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
