package com.chous.bookservice.repository;

import com.chous.bookservice.entity.Book;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.lang.NonNull;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    @NonNull
    List<Book> findAll();
}
