package com.github.delve.image.dto;

import org.springframework.web.multipart.MultipartFile;

public class SaveImageCommand {

    public MultipartFile file;

    public SaveImageCommand() {
    }

    public SaveImageCommand(final MultipartFile file) {
        this.file = file;
    }
}
