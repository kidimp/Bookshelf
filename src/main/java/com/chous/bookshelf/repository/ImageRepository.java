package com.chous.bookshelf.repository;

import com.chous.bookshelf.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {

    Optional<Image> findImageById(String id);

//    Image findImageById(String id);
//    List<Image> findAll();

}
