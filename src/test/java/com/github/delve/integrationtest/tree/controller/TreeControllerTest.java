package com.github.delve.integrationtest.tree.controller;

import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TreeControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void getAllTrees() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/tree/all")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
