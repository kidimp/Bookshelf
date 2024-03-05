package com.chous.imageservice.repository;

import com.chous.imageservice.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {

    Optional<Image> findImageById(String id);

}
