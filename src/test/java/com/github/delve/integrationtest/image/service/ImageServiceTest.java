package com.github.delve.integrationtest.image.service;

import com.github.delve.image.dto.SaveImageCommand;
import com.github.delve.image.service.ImageService;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.github.delve.integrationtest.image.util.ImageUtil.createMultipartFileFromPng;
import static com.github.delve.integrationtest.image.util.ImageUtil.deleteTestGeneratedFiles;

public class ImageServiceTest extends SpringBootTestBase {

    @Autowired
    private ImageService imageService;

    @Test
    public void loadFile() {
        final Resource imageResource = imageService.loadFile("fat_cat.png");

        Assert.assertNotNull(imageResource);
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    public void saveAndDeleteFile() throws IOException {
        final MultipartFile file = createMultipartFileFromPng(imageService, "fat_cat.png");
        final SaveImageCommand command = new SaveImageCommand(file);

        final String savedFileName = imageService.saveFile(command);
        final Resource imageResource = imageService.loadFile(savedFileName);
        Assert.assertNotNull(imageResource);

        deleteTestGeneratedFiles(imageService);
    }
}
