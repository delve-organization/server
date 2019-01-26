package com.github.delve.image.service;

import com.github.delve.image.dto.SaveImageCommand;
import com.github.delve.security.service.user.UserPrinciple;
import com.github.delve.security.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Path rootLocation;
    private final String imageFilePrefix;

    @Autowired
    public ImageService(@Value("${delve.resource.path.images}") final String imagesResourcePath, @Value("${delve.image.file.prefix}") final String imageFilePrefix) {
        this.rootLocation = Paths.get(imagesResourcePath);
        this.imageFilePrefix = imageFilePrefix;
    }

    public Resource loadFile(final String filename) {
        try {
            final Path file = rootLocation.resolve(filename);
            final Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(String.format("Could not read file %s.", filename));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("Could not create url for file %s.", filename));
        }
    }

    public String saveFile(final SaveImageCommand command) {
        final String generatedFileName = generateFileNameFromUserTimeHash(command.file.getOriginalFilename());

        try {
            Files.copy(command.file.getInputStream(), this.rootLocation.resolve(generatedFileName));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not save file %s.", generatedFileName));
        }

        logger.info("Saved new image with name: {}", generatedFileName);
        return generatedFileName;
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    private String generateFileNameFromUserTimeHash(final String file) {
        final UserPrinciple user = UserUtil.currentUser();
        final LocalDateTime time = LocalDateTime.now();
        final int userTimeHash = user.hashCode() ^ time.hashCode();

        final int lastDotIndex = file.lastIndexOf('.');
        final String extension = file.substring(lastDotIndex);

        return imageFilePrefix + userTimeHash + extension;
    }
}
