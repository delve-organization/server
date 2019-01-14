package com.github.delve.integrationtest.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.delve.component.admin.dto.UpdateUserRequest;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.security.domain.User;
import com.github.delve.security.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.github.delve.dev.UserTestData.ADMIN_ID;
import static com.github.delve.security.domain.RoleName.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AdminControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    public void getUsers() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get(String.format("/api/admin/get-users?direction=%s&orderBy=%s&page=%d&size=%d", "asc", "name", 0, 2))
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    public void updateUsers() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        final User adminUser = userRepository.findById(ADMIN_ID).get();
        final UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setId(adminUser.getId());
        updateRequest.setName(adminUser.getName());
        updateRequest.setUsername(adminUser.getUsername());
        updateRequest.setEmail(adminUser.getEmail());
        updateRequest.setRoles(Collections.singleton(ROLE_USER));
        final List<UpdateUserRequest> updateUserRequests = Collections.singletonList(updateRequest);

        mvc.perform(post("/api/admin/update-users")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(updateUserRequests))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
