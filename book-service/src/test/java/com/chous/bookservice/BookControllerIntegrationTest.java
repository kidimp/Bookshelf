package com.chous.bookservice;

import com.chous.bookservice.entity.Book;
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
class BookControllerIntegrationTest {
    private final String URL = "http://localhost:";
    private final String API_PATH = "/api/v1/books";
    private final String QUERY = "SELECT id FROM public.books LIMIT 1;";

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void testHttpConnection() {
        String response = this.testRestTemplate.getForObject(URL + port + API_PATH,
                String.class);

        assertThat(response).isNotNull();
    }


    @Test
    void testGetBookById() {
        Long id = jdbcTemplate.queryForObject(QUERY, Long.class);

        Book existingBook = this.testRestTemplate.getForObject(URL + port + API_PATH + "/" + id, Book.class);

        assertThat(existingBook).isNotNull();
    }


    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setTitle("Test Create Book Title");
        book.setAuthor("Test Create Book Author");
        book.setDescription("Test Create Book Description");

        ResponseEntity<Void> response = this.testRestTemplate.exchange(URL + port + API_PATH,
                HttpMethod.POST, new HttpEntity<>(book), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }


    @Test
    void testUpdateBook() {
        Long id = jdbcTemplate.queryForObject(QUERY, Long.class);

        Book bookUpdates = new Book();
        bookUpdates.setTitle("Test Update Book Title");
        bookUpdates.setAuthor("Test Update Book Author");
        bookUpdates.setDescription("Test Update Book Description");

        ResponseEntity<Void> response = this.testRestTemplate.exchange(URL + port + API_PATH + "/" + id,
                HttpMethod.PUT, new HttpEntity<>(bookUpdates), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void testDeleteBook() {
        Long id = jdbcTemplate.queryForObject(QUERY, Long.class);

        ResponseEntity<Void> response = this.testRestTemplate.exchange(URL + port + API_PATH + "/" + id,
                HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}