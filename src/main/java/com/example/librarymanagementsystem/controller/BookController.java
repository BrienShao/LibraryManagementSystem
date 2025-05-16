package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.PageBean;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 新增图书
     * @param book 图书信息
     */
    @PostMapping
    public void addBook(@RequestBody @Validated Book book) {
        bookService.addBook(book);
    }

    /**
     * 分页获取图书列表（带分类搜索）
     * @param pageNo 页数
     * @param pageSize 没页条数
     * @param categoryId 分类ID（非必须）
     * @param searchKeyword 搜索关键词（非必须）
     */
    @GetMapping
    public PageBean<Book> list(Integer pageNo, Integer pageSize, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String searchKeyword) {
        return bookService.list(pageNo, pageSize, categoryId, searchKeyword);
    }

    /**
     * 获取图书信息
     * @param bookId 图书ID
     */
    @GetMapping("/{bookId}")
    public Book detail(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    /**
     * 更新图书信息
     * @param book 图书信息
     */
    @PutMapping
    public void updateBook(@RequestBody @Validated Book book) {
        bookService.updateBook(book);
    }

    /**
     * 删除图书
     * @param id 图书ID
     */
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
