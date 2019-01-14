package com.github.delve.integrationtest.treeboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.delve.component.treeboard.dto.DeleteTreeBoardCommand;
import com.github.delve.integrationtest.treeboard.util.TreeBoardBaseData;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.delve.dev.TreeBoardTestData.TREE_BOARD_0_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TreeBoardControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBoardBaseData.class)
    public void getAllAvailable() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/tree-board/all-available")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBoardBaseData.class)
    public void delete() throws Exception {
        final String token = jwtAuthenticator.generateToken();
        final DeleteTreeBoardCommand request = new DeleteTreeBoardCommand(TREE_BOARD_0_ID);

        mvc.perform(post("/api/tree-board/delete")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}