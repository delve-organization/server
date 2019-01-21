package com.github.delve.integrationtest.treecard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.delve.component.treecard.dto.CreateTreeCardCommand;
import com.github.delve.component.treecard.dto.DeleteTreeCardCommand;
import com.github.delve.component.treecard.dto.EditTreeCardCommand;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.treecard.util.TreeCardBaseData;
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
import static com.github.delve.dev.TreeCardTestData.TREE_CARD_0_ID;
import static com.github.delve.dev.TreeTestData.TREE_0_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TreeCardControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeCardBaseData.class)
    public void getAllAvailable() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(get("/api/tree-card/all-available")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeCardBaseData.class)
    public void create() throws Exception {
        final String token = jwtAuthenticator.generateToken();
        final CreateTreeCardCommand request = new CreateTreeCardCommand(TREE_0_ID, "Title", "Description", "fat_cat.png", "red", PUBLIC);

        mvc.perform(post("/api/tree-card/create")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeCardBaseData.class)
    public void edit() throws Exception {
        final String token = jwtAuthenticator.generateToken();
        final EditTreeCardCommand request = new EditTreeCardCommand(TREE_CARD_0_ID, TREE_0_ID, "Title", "Description", "fat_cat.png", "yellow", PUBLIC);

        mvc.perform(post("/api/tree-card/edit")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeCardBaseData.class)
    public void delete() throws Exception {
        final String token = jwtAuthenticator.generateToken();
        final DeleteTreeCardCommand request = new DeleteTreeCardCommand(TREE_CARD_0_ID);

        mvc.perform(post("/api/tree-card/delete")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}