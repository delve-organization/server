package com.github.delve.image.dto;

public class ImageUploadDto {

    public final String imageName;
    public final String imageUrl;

    public ImageUploadDto(final String imageName, final String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }
}
