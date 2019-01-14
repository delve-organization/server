package com.github.delve.integrationtest.node.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.delve.component.node.dto.CreateUserNodeRelationCommand;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.node.util.NodeBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.delve.dev.NodeTestData.LEVEL_2_0_ID;
import static com.github.delve.dev.NodeTestData.ROOT_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class NodeControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(NodeBaseData.class)
    public void getNodesFromRoot() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/node/id/" + ROOT_ID)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(NodeBaseData.class)
    public void setRelation() throws Exception {
        final String token = jwtAuthenticator.generateToken();
        final CreateUserNodeRelationCommand relationRequest = new CreateUserNodeRelationCommand();
        relationRequest.setNodeId(LEVEL_2_0_ID);
        relationRequest.setVisited(true);

        mvc.perform(post("/api/node/relation")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(relationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    public void getRelationsByUser() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/node/relations")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
