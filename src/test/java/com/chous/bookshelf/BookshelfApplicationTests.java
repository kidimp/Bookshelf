package com.chous.bookshelf;

import com.chous.bookshelf.controller.BookController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookshelfApplicationTests {

    @Autowired
    private BookController bookController;

    @Test
    void contextLoads() {
        assertThat(bookController).isNotNull();
    }

}