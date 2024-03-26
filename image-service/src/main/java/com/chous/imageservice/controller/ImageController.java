package com.chous.imageservice.controller;

import com.chous.imageservice.feign.ImageClient;
import com.chous.imageservice.service.ImageService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/books-image")
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String imageId;
    private final ImageService imageService;
    private final ImageClient imageClient;

    @Autowired
    public ImageController(ImageService imageService, ImageClient imageClient) {
        this.imageService = imageService;
        this.imageClient = imageClient;
    }


    @PostMapping("/{id}")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        try {
            imageId = imageService.uploadImage(file, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Image uploaded successfully");
    }


    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable Long id) {

        try {
            GridFsResource gridFsResource = imageService.getImageByBookId(id);

            if (gridFsResource == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            InputStreamResource inputStreamResource = new InputStreamResource(gridFsResource.getInputStream());

            HttpHeaders headers = new HttpHeaders();
            String contentDisposition = "attachment; filename*=\"UTF-8''" + gridFsResource.getFilename() + "\"";
            headers.add(HttpHeaders.CONTENT_DISPOSITION, URLEncoder.encode(contentDisposition, StandardCharsets.UTF_8));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(gridFsResource.getContentType()))
                    .body(inputStreamResource);

        } catch (Exception e) {
            throw new RuntimeException("Error downloading image", e);
        }
    }

    @KafkaListener(
            topics = "book-topic",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void deleteImage(Long id) {
        imageService.deleteBookImageById(id);
    }


    // Метод добавлен для проверки Spring Cloud OpenFeign
    // Смотреть совместно с book-service.src.main.java.com.chous.bookservice.controller.BookController
    // и image-service.src.main.java.com.chous.imageservice.feign.ImageClient
    @GetMapping("/feign")
    public ResponseEntity<String> doFeign() {
        try {
            ResponseEntity<String> status = imageClient.getStatus();
            logger.info("Checking Feign client work " + status);
        } catch (Exception e) {
            throw new RuntimeException("Spring Cloud OpenFeign Failed", e);
        }
        return ResponseEntity.ok("Feign client worked successfully");
    }

    // Метод добавлен для проверки Resilience4j CircuitBreaker
    @GetMapping("/activity")
    @CircuitBreaker(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    public ResponseEntity<String> getActivity() {
        return imageClient.getActivity();
    }

    public ResponseEntity<String> fallbackRandomActivity(Exception ex) {
        return ResponseEntity.ok("Randomly generated activity in case of unavailability of book-service!");
    }
}

