package com.github.delve.integrationtest.image.service;

import com.github.delve.image.service.ImageService;
import com.github.delve.integrationtest.SpringBootTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class ImageServiceTest extends SpringBootTestBase {

    @Autowired
    private ImageService imageService;

    @Test
    public void loadFile() {
        Resource imageResource = imageService.loadFile("fat_cat.png");

        Assert.assertNotNull(imageResource);
    }

}
