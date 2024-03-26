package com.chous.bookservice.controller;

import com.chous.bookservice.entity.Book;
import com.chous.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public BookController(BookService bookService, KafkaTemplate<String, String> kafkaTemplate) {
        this.bookService = bookService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping()
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    public ResponseEntity<Void> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody Book book) {
        bookService.updateBook(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        kafkaTemplate.send("book-topic", id.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-all-books-names")
    public List<String> getAllBooksNames() {
        return bookService.getAllBooksNames();
    }

    // Метод добавлен для проверки Spring Cloud OpenFeign
    // Смотреть совместно с image-service.src.main.java.com.chous.imageservice.feign.ImageClient
    // и image-service.src.main.java.com.chous.imageservice.controller.ImageController
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Service is up and running!");
    }

    // Метод добавлен для проверки Resilience4j CircuitBreaker
    @GetMapping("/activity")
    ResponseEntity<String> getActivity(){
        return ResponseEntity.ok("Activity is generated!");
    }

}

