package com.github.delve.integrationtest.image.controller;

import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.image.service.ImageService;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.delve.integrationtest.image.util.ImageUtil.createMultipartFileFromPng;
import static com.github.delve.integrationtest.image.util.ImageUtil.deleteTestGeneratedFiles;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ImageControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;
    @Autowired
    private ImageService imageService;

    @Test
    public void getImage() throws Exception {
        mvc.perform(get("/images/fat_cat.png"))
                .andExpect(status().isOk());
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    public void uploadImage() throws Exception {
        final String token = jwtAuthenticator.generateToken();

        mvc.perform(multipart("/api/images/upload")
                .file(createMultipartFileFromPng(imageService, "fat_cat.png"))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        deleteTestGeneratedFiles(imageService);
    }
}
