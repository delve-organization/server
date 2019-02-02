package com.github.delve.integrationtest.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.Preload;
import com.github.delve.security.dto.LoginRequest;
import com.github.delve.security.dto.SignUpRequest;
import com.github.delve.security.repository.UserRepository;
import com.github.delve.security.service.user.UserPrinciple;
import com.github.delve.security.util.UserUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AuthenticationControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Preload(UserBaseData.class)
    public void signin() throws Exception {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password");

        mvc.perform(post("/api/auth/signin")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void signup() throws Exception {
        assertEquals(0L, userRepository.count());

        final SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Admin");
        signUpRequest.setUsername("admin");
        signUpRequest.setPassword("password");
        signUpRequest.setEmail("delve@admin.com");

        mvc.perform(post("/api/auth/signup")
                .content(objectMapper.writeValueAsString(signUpRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1L, userRepository.count());
    }
}
