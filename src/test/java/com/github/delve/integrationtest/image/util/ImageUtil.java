package com.github.delve.integrationtest.image.util;

import com.github.delve.image.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class ImageUtil {

    private ImageUtil() {
    }

    public static MockMultipartFile createMultipartFileFromPng(final ImageService imageService, final String imageName) throws IOException {
        final Resource imageResource = imageService.loadFile(imageName);
        return new MockMultipartFile("file", "tg_" + imageName, IMAGE_PNG_VALUE, imageResource.getInputStream());
    }

    public static void deleteTestGeneratedFiles(final ImageService imageService) {
        final Path rootLocation = imageService.getRootLocation();
        final File rootFolder = rootLocation.toFile();
        for (final File file : rootFolder.listFiles()) {
            if (file.getName().startsWith("tg_")) {
                file.delete();
            }
        }
    }
}
