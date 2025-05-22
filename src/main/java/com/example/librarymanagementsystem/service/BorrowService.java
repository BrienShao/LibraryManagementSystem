package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Borrow;
import com.example.librarymanagementsystem.entity.BorrowState;
import com.example.librarymanagementsystem.entity.PageBean;

import java.util.List;

public interface BorrowService {
    // 查找用户借阅某图书的记录
    Borrow getBorrowByBookId(Long bookId, Enum<BorrowState> state);

    // 新增借阅记录
    String borrow(Long bookId);
    void adminBorrow(Borrow borrow);

    // 获取用户所有借阅记录
    PageBean<Borrow> records(Integer pageNo, Integer pageSize, Enum<BorrowState> state, String searchKeyword);

    // 获取某图书的所有借阅记录
    List<Borrow> getAllBorrowByBookId(Long bookId, Enum<BorrowState> state);

    // 归还图书
    String repaid(Long bookId, BorrowState state);

    // 删除记录
    void delete(Long id);

    // 获取借阅记录信息
    Borrow getBorrowById(Long id);
    PageBean<Borrow> allRecords(Integer pageNo, Integer pageSize, BorrowState state, Long userId, String searchKeyword);

    String update(Borrow borrow);
}
