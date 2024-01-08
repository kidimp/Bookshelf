package com.chous.bookshelf.service;

import com.chous.bookshelf.component.HibernateSession;
import com.chous.bookshelf.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HibernateSession hibernateSession;
    private final BookRepository repository;

    @Autowired
    public HibernateService(HibernateSession hibernateSession, BookRepository repository) {
        this.hibernateSession = hibernateSession;
        this.repository = repository;
    }

    public void testServiceMethod() {
        hibernateSession.cacheTest();
    }

    public void queryCacheTestServiceMethod() {
        logger.info("Executing findAll() for the first time");
        repository.findAll();
        logger.info("Executing findAll() for the second time");
        repository.findAll();
    }
}
