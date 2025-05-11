package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.PageBean;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public void addBook(@RequestBody @Validated Book book) {
        bookService.addBook(book);
    }

    @GetMapping
    public PageBean<Book> list(Integer pageNo, Integer pageSize, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String search) {
        return bookService.list(pageNo, pageSize, categoryId, search);
    }

    @GetMapping("/{id}")
    public Book detail(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping
    public void updateBook(@RequestBody @Validated Book book) {
        bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
