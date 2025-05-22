package com.example.librarymanagementsystem.service.impl;

import com.example.librarymanagementsystem.entity.*;
import com.example.librarymanagementsystem.exception.BusinessException;
import com.example.librarymanagementsystem.mapper.BookMapper;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.BorrowService;
import com.example.librarymanagementsystem.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private BorrowService borrowService;

    @Override
    public void addBook(Book book) {
        // 补充属性
        Long userId = userService.getUserId();

        book.setCreateUserId(userId);
        book.setUpdateUserId(userId);
        bookMapper.addBook(book);
    }

    @Override
    public Book findById(Long bookId) {
        Book book = bookMapper.findById(bookId);
        if (book == null) {
            throw new BusinessException("请求参数错误");
        }
        return book;
    }

    @Override
    public PageBean<Book> list(Integer pageNo, Integer pageSize, Integer categoryId, BookState state, String searchKeyword) {
        // 创建PageBean对象
        PageBean<Book> pageBean = new PageBean<>();
        // 开启分页查询 PageHelp
        PageHelper.startPage(pageNo, pageSize);
        // 调用mapper
        PageInfo<Book> pageInfo = new PageInfo<>(bookMapper.list(categoryId, state, searchKeyword));
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setList(pageInfo.getList());
        return pageBean;
    }

    @Override
    public void updateBook(Book book) {
        User currentUser = userService.userInfo();
        Book oldBook = bookMapper.findById(book.getId());
        // 判断当前用户是否创建者本人或管理员
        if (currentUser.getId().equals(oldBook.getCreateUserId()) || currentUser.getIsAdmin() == 1) {
            book.setUpdateUserId(currentUser.getId());
            book.setUpdateTime(LocalDateTime.now());
            bookMapper.update(book);
        } else {
            throw new BusinessException(403, "没有操作权限");
        }
    }

    @Override
    public void deleteBook(Long id) {
        User currentUser = userService.userInfo();
        Book book = bookMapper.findById(id);
        // 判断当前用户是否创建者本人或管理员
        if (currentUser.getId().equals(book.getCreateUserId()) || currentUser.getIsAdmin() == 1) {
            bookMapper.delete(id);
        } else {
            throw new BusinessException(403, "没有操作权限");
        }
        // 判断是否存在未归还图书
        List<Borrow> list = borrowService.getAllBorrowByBookId(id, BorrowState.UNREPAID);
        if (!list.isEmpty()) {
            throw new BusinessException("有图书尚未归还");
        }
        // 执行删除操作
        bookMapper.delete(id);
    }
}
