package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.PageBean;


public interface BookService {

    // 新增图书
    void addBook(Book book);

    // 通过图书ID获取图书
    Book findById(Long bookId);

    // 条件分页列表查询
    PageBean<Book> list(Integer pageNo, Integer pageSize, Integer categoryId, String search);

    void updateBook(Book book);

    void deleteBook(Long id);
}
