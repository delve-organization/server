package com.github.delve.integrationtest.tree.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.delve.component.tree.dto.CreateTreeRequest;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.dev.NodeTestData.ROOT_ID;
import static com.github.delve.dev.TreeTestData.TREE_0_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TreeControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void getAllAvailableTrees() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/tree/all-available")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void getTreeById() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/tree/id/" + TREE_0_ID)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void create() throws Exception {
        final String token = jwtAuthenticator.generateToken();
        final CreateTreeRequest request = new CreateTreeRequest();
        request.setRootNodeId(ROOT_ID);
        request.setTitle("New tree");
        request.setAccessibility(PUBLIC);

        mvc.perform(post("/api/tree/create")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
