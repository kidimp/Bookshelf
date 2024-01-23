package com.chous.bookshelf.repository;

import com.chous.bookshelf.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookRepo {
    private final EntityManager entityManager;

    @Autowired
    public BookRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<String> getAllBooksNames() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root.get("title"));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}