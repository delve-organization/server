package com.github.delve.integrationtest.locale.controller;

import com.github.delve.integrationtest.SpringBootTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class LocaleControllerTest extends SpringBootTestBase {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getTranslations() throws Exception {
        mvc.perform(get("/public/translations?locale=hu"))
                .andExpect(status().isOk());
    }
}
