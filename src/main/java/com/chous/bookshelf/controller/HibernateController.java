package com.chous.bookshelf.controller;

import com.chous.bookshelf.service.HibernateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HibernateController {
    private final HibernateService hibernateService;

    @Autowired
    public HibernateController(HibernateService hibernateService) {
        this.hibernateService = hibernateService;
    }

    @GetMapping("/api/v1/hibernate/test")
    public void doTest() {
        hibernateService.testServiceMethod();
    }

    @GetMapping("/api/v1/hibernate/test-query-cache")
    public void doAnotherTest() {
        hibernateService.queryCacheTestServiceMethod();
    }
}
