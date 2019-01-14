package com.github.delve.common.util;

import com.github.delve.image.controller.ImageController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public class MvcUrlCreator {

    private MvcUrlCreator() {
    }

    public static String imageUrl(final String filename) {
        return MvcUriComponentsBuilder.fromMethodName(ImageController.class, "getFile", filename).build().toString();
    }
}
