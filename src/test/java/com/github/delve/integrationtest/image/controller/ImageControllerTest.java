package com.github.delve.integrationtest.image.controller;

import com.github.delve.integrationtest.SpringBootTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ImageControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getImage() throws Exception {
        mvc.perform(get("/images/fat_cat.png"))
                .andExpect(status().isOk());
    }
}
