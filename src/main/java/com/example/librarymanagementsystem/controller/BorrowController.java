package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.entity.Borrow;
import com.example.librarymanagementsystem.entity.BorrowState;
import com.example.librarymanagementsystem.entity.PageBean;
import com.example.librarymanagementsystem.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    /**
     * 新增借阅记录
     * @param id 图书ID
     */
    @PostMapping("/{id}")
    public String borrow(@PathVariable Long id) {
        return borrowService.borrow(id);
    }

    /**
     * 获取当前用户借阅列表
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @param state 记录状态（非必须）
     * @param searchKeyword 搜索关键词（非必须）
     */
    @GetMapping("/records")
    public PageBean<Borrow> records(Integer pageNo, Integer pageSize, @RequestParam(required = false) BorrowState state, @RequestParam(required = false) String searchKeyword) {
        return borrowService.records(pageNo, pageSize, state, searchKeyword);
    }

    /**
     * 通过图书ID获取当前用户某图书的借阅记录
     * @param bookId 图书ID
     * @param state 记录状态（非必须）
     */
    @GetMapping("/book/{bookId}")
    public Borrow getBorrowByBookId(@PathVariable Long bookId, @RequestParam(required = false) Enum<BorrowState> state) {
        return borrowService.getBorrowByBookId(bookId, state);
    }

    /**
     * 获取借阅信息
     * @param id 借阅ID
     */
    @GetMapping("/{id}")
    public Borrow getBorrowById(@PathVariable Long id) {
        return borrowService.getBorrowById(id);
    }

    /**
     * 归还图书
     * @param bookId 图书ID
     * @param state 借阅记录状态
     */
    @PatchMapping("/{bookId}")
    public String repaidByBookId(@PathVariable Long bookId, @RequestParam BorrowState state) {
        return borrowService.repaid(bookId, state);
    }

    /**
     * 删除借阅记录
     * @param id 图书ID
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        borrowService.delete(id);
    }
}
