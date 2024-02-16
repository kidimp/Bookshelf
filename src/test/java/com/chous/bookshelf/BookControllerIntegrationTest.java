package com.chous.bookshelf;

import com.chous.bookshelf.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    @Transactional()
    @Rollback
    public void testCreateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Test Create Book Title123123");
        book.setAuthor("Test Create Book Author");
        book.setDescription("Test Create Book Description");

        String bookJson = objectMapper.writeValueAsString(book);

        ResultActions result = mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    @Transactional
    @Rollback
    public void testUpdateBook() throws Exception {
        Long id = jdbcTemplate.queryForObject("SELECT id FROM public.books LIMIT 1;", Long.class);

        assertThat(id).isNotNull();

        Book bookUpdates = new Book();
        bookUpdates.setTitle("Test Update Book Title1");
        bookUpdates.setAuthor("Test Update Book Author");
        bookUpdates.setDescription("Test Update Book Description");

        String bookJson = objectMapper.writeValueAsString(bookUpdates);

        ResultActions result = mockMvc.perform(put("/api/v1/books/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Transactional
    @Rollback
    public void testDeleteBook() throws Exception {
        Long id = jdbcTemplate.queryForObject("SELECT id FROM public.books LIMIT 1;", Long.class);

        assertThat(id).isNotNull();

        ResultActions result = mockMvc.perform(delete("/api/v1/books/{id}", id));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}