package com.chous.bookservice.service;

import com.chous.bookservice.annotation.Cacheable;
import com.chous.bookservice.entity.Book;
import com.chous.bookservice.repository.BookRepo;
import com.chous.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookRepo bookRepo;

    @Autowired
    public BookService(BookRepository bookRepository, BookRepo bookRepo) {
        this.bookRepository = bookRepository;
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable
    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Long id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setDescription(updatedBook.getDescription());
            bookRepository.save(existingBook);
        }
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<String> getAllBooksNames() {
        return bookRepo.getAllBooksNames();
    }

}

