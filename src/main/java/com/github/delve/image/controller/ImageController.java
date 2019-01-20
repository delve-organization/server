package com.github.delve.image.controller;

import com.github.delve.common.util.MvcUrlCreator;
import com.github.delve.image.dto.ImageUploadDto;
import com.github.delve.image.dto.SaveImageCommand;
import com.github.delve.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable final String filename) {
        final Resource file = imageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/api/images/upload")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseBody
    public ResponseEntity<ImageUploadDto> uploadFile(@RequestParam("file") final MultipartFile file) {
        final String imageName = imageService.saveFile(new SaveImageCommand(file));
        final String imageUrl = MvcUrlCreator.imageUrl(imageName);

        return ResponseEntity.status(HttpStatus.OK).body(new ImageUploadDto(imageName, imageUrl));
    }
}
