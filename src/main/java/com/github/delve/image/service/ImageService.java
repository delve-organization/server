package com.github.delve.image.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final Path rootLocation;

    @Autowired
    public ImageService(@Value("${delve.resource.path.images}") final String imagesResourcePath) {
        this.rootLocation = Paths.get(imagesResourcePath);
    }

    public Resource loadFile(final String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(String.format("Could not read file %s.", filename));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("Could not create url for file %s.", filename));
        }
    }
}
