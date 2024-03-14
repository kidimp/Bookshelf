package com.chous.bookservice;

import com.chous.bookservice.controller.BookController;
import com.chous.bookservice.entity.Book;
import com.chous.bookservice.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerUnitTest {
    private final String API_PATH = "/api/v1/books";
    private final String API_PATH_W_ID = "/api/v1/books/{id}";

    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGetAllBooks() throws Exception {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> mockBooks = Arrays.asList(book1, book2);
        int i = 1;
        for (Book book : mockBooks) {
            book.setTitle("Book" + i);
            book.setAuthor("Author" + i);
            book.setDescription("Description" + i);
            i++;
        }

        when(bookService.getAllBooks()).thenReturn(mockBooks);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Book1"))
                .andExpect(jsonPath("$[0].author").value("Author1"))
                .andExpect(jsonPath("$[0].description").value("Description1"))
                .andExpect(jsonPath("$[1].title").value("Book2"))
                .andExpect(jsonPath("$[1].author").value("Author2"))
                .andExpect(jsonPath("$[1].description").value("Description2"));

        verify(bookService, times(1)).getAllBooks();
    }


    @Test
    void testGetBookById() throws Exception {
        Book book = new Book();
        book.setTitle("Unit Test Book");
        book.setAuthor("Unit Test Author");
        book.setDescription("Unit Test Description");

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get(API_PATH_W_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Unit Test Book"))
                .andExpect(jsonPath("$.author").value("Unit Test Author"))
                .andExpect(jsonPath("$.description").value("Unit Test Description"));

        verify(bookService, times(1)).getBookById(1L);
    }


    @Test
    void testCreateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Unit Test Title");
        book.setAuthor("Unit Test Author");
        book.setDescription("Unit Test Description");

        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated());
        verify(bookService, times(1)).createBook(any());
    }


    @Test
    void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");
        book.setDescription("Updated Description");

        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(put(API_PATH_W_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk());
    }


    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete(API_PATH_W_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(1L);
    }
}