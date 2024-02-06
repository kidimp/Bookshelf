package com.chous.bookshelf.controller;

import com.chous.bookshelf.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/books")
public class ImageController {

    String imageId;

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        try {
            imageId = imageService.uploadImage(file, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Image uploaded successfully");
    }

    @GetMapping("/{id}/download-image")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable Long id) {

        try {
            GridFsResource gridFsResource = imageService.getImageByBookId(id);

            if (gridFsResource == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            InputStreamResource inputStreamResource = new InputStreamResource(gridFsResource.getInputStream());

            HttpHeaders headers = new HttpHeaders();
            String contentDisposition = "attachment; filename*=\"UTF-8''"+ gridFsResource.getFilename() + "\"";
            headers.add(HttpHeaders.CONTENT_DISPOSITION, URLEncoder.encode(contentDisposition, StandardCharsets.UTF_8));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(gridFsResource.getContentType()))
                    .body(inputStreamResource);

        } catch (Exception e) {
            throw new RuntimeException("Error downloading image", e);
        }
    }

}
