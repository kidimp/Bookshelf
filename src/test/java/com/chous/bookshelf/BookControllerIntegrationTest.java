package com.chous.bookshelf;

import com.chous.bookshelf.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testHttpConnection() {
        String response = this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books",
                String.class);

        assertThat(response).isNotNull();
    }


    @Test
    public void testGetBookById() {
        Long id = jdbcTemplate.queryForObject("SELECT id FROM public.books LIMIT 1;", Long.class);

        Book existingBook = this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books/"
                + id, Book.class);

        assertThat(existingBook).isNotNull();
    }


    @Test
    public void testCreateBook() {
        Book book = new Book();
        book.setTitle("Test Create Book Title");
        book.setAuthor("Test Create Book Author");
        book.setDescription("Test Create Book Description");

        ResponseEntity<Void> response = this.testRestTemplate.exchange("http://localhost:" + port + "/api/v1/books",
                HttpMethod.POST, new HttpEntity<>(book), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }


    @Test
    public void testUpdateBook() {
        Long id = jdbcTemplate.queryForObject("SELECT id FROM public.books LIMIT 1;", Long.class);

        Book bookUpdates = new Book();
        bookUpdates.setTitle("Test Update Book Title1");
        bookUpdates.setAuthor("Test Update Book Author");
        bookUpdates.setDescription("Test Update Book Description");

        ResponseEntity<Void> response = this.testRestTemplate.exchange("http://localhost:" + port + "/api/v1/books/" + id,
                HttpMethod.PUT, new HttpEntity<>(bookUpdates), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void testDeleteBook() {
        Long id = jdbcTemplate.queryForObject("SELECT id FROM public.books LIMIT 1;", Long.class);

        ResponseEntity<Void> response = this.testRestTemplate.exchange("http://localhost:" + port + "/api/v1/books/" + id,
                HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}