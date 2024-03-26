package com.chous.imageservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

// Смотреть совместно с book-service.src.main.java.com.chous.bookservice.controller.BookController
// и image-service.src.main.java.com.chous.imageservice.controller.ImageController
@FeignClient(name = "book-service", url = "http://api-gateway:8060/api/v1/books")
public interface ImageClient {

    @GetMapping("/status")
    ResponseEntity<String> getStatus();

    @GetMapping("/activity")
    ResponseEntity<String> getActivity();

}
