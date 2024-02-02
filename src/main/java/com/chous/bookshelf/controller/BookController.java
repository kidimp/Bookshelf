package com.chous.bookshelf.controller;

import com.chous.bookshelf.entity.Book;
import com.chous.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
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
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

//    Можно сделать альтернативным способом, передавая id в теле запроса, чтобы явным образом не передавать id.
//    @DeleteMapping("/delete")
//    public void deleteBook(@RequestBody Long id) {
//        bookService.deleteBook(id);
//    }

    @GetMapping("/get-all-books-names")
    public List<String> getAllBooksNames() {
        return bookService.getAllBooksNames();
    }

}
