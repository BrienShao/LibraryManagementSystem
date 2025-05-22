package com.example.librarymanagementsystem.service.impl;

import com.example.librarymanagementsystem.entity.*;
import com.example.librarymanagementsystem.exception.BusinessException;
import com.example.librarymanagementsystem.mapper.BookMapper;
import com.example.librarymanagementsystem.mapper.BorrowMapper;
import com.example.librarymanagementsystem.service.BookStateService;
import com.example.librarymanagementsystem.service.BorrowService;
import com.example.librarymanagementsystem.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookStateService bookStateService;

    /**
     * 借阅记录
     * @param id 借阅ID
     */
    @Override
    public Borrow getBorrowById(Long id) {
        return borrowMapper.getBorrowById(id);
    }

    /**
     * 获取借阅列表
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @param state 记录状态（非必须）
     * @param userId 用户ID（非必须）
     * @param searchKeyword 搜索关键词（非必须）
     */
    @Override
    public PageBean<Borrow> allRecords(Integer pageNo, Integer pageSize, BorrowState state, Long userId, String searchKeyword) {
        // 创建PageBean对象
        PageBean<Borrow> pageBean = new PageBean<>();
        // 开启分页查询 PageHelp
        PageHelper.startPage(pageNo, pageSize);
        // 调用mapper
        PageInfo<Borrow> pageInfo = new PageInfo<>(borrowMapper.records(userId, state, searchKeyword));
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setList(pageInfo.getList());
        return pageBean;
    }

    @Override
    public String update(Borrow borrow) {
        // 获取当前用户ID
        Long userId = userService.getUserId();
        // 获取图书
        Book book = bookMapper.findById(borrow.getBookId());
        // 获取旧的借阅记录
        Borrow oldBorrow = borrowMapper.getBorrowById(borrow.getId());

        // 如果旧的借阅记录不是“已归还”，更新图书库存数据
        if (!oldBorrow.getState().equals(BorrowState.REPAID)) {
            // 如果图书为已借出状态，更新为可借阅
            if (book.getState() == BookState.BORROWED) {
                bookStateService.changeState(borrow.getBookId(), BookState.BORROWED, userId, "管理员归还");
            }
            // 更新图书库存信息
            book.setStockCount(book.getStockCount() + 1);
            bookMapper.update(book);
        }

        borrowMapper.repaid(borrow);
        return "操作成功";
    }

    /**
     * 通过图书ID获取当前用户借阅记录
     * @param bookId 图书ID
     * @param state 借阅记录状态
     */
    @Override
    public Borrow getBorrowByBookId(Long bookId, Enum<BorrowState> state) {
        Long userId = userService.getUserId();
        return borrowMapper.getBorrowByBookId(userId, bookId, state);
    }

    /**
     * 新增图书借阅
     * @param bookId 图书ID
     */
    @Override
    public String borrow(Long bookId) {
        Long userId = userService.getUserId();
        // 查找未还借阅记录
        Borrow borrow = borrowMapper.getBorrowByBookId(userId, bookId, null);
        if (borrow != null && (borrow.getState() == BorrowState.UNCLAIMED || borrow.getState() == BorrowState.CLAIMED)) {
            throw new BusinessException("存在借阅记录");
        }
        Book book = bookMapper.findById(bookId);
        // 图书状态判断
        switch (book.getState()) {
            case BORROWED -> throw new BusinessException("图书无库存");
            case UNDER_MAINTENANCE -> throw new BusinessException("图书维护中");
            case LOST -> throw new BusinessException("图书已遗失");
            case REMOVED -> throw new BusinessException("图书已下架");
        }
        // 库存判断
        int stockCount = book.getStockCount() - 1;
        if (stockCount < 0) {
            throw new BusinessException("没有库存了");
        }
        if (stockCount == 0) {
            // 修改图书状态
            bookStateService.changeState(bookId, BookState.BORROWED, userId, "系统操作：没有库存");
        }
        book.setStockCount(stockCount);
        // 更新图书库存信息
        bookMapper.update(book);
        // 新增借阅记录
        borrowMapper.borrow(userId, book);
        return "请向管理员领取图书";
    }
    @Override
    public void adminBorrow(Borrow borrow) {
        // 查找未还借阅记录
        Borrow oldBorrow = borrowMapper.getBorrowByBookId(borrow.getUserId(), borrow.getBookId(), null);
        if (oldBorrow != null && (oldBorrow.getState() == BorrowState.UNCLAIMED || oldBorrow.getState() == BorrowState.CLAIMED)) {
            throw new BusinessException("存在借阅记录");
        }
        Book book = bookMapper.findById(borrow.getBookId());
        // 库存判断
        int stockCount = book.getStockCount() - 1;
        if (stockCount == 0) {
            // 修改图书状态
            bookStateService.changeState(borrow.getBookId(), BookState.BORROWED, borrow.getUserId(), "系统操作：没有库存");
        }
        book.setStockCount(stockCount);
        // 更新图书库存信息
        bookMapper.update(book);
        // 新增借阅记录
        borrowMapper.adminBorrow(borrow);
    }

    /**
     * 以分页的形式获取当前用户的所有借阅记录
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @param state 记录状态
     * @param searchKeyword 搜索关键词
     */
    @Override
    public PageBean<Borrow> records(Integer pageNo, Integer pageSize, Enum<BorrowState> state, String searchKeyword) {
        Long userId = userService.getUserId();
        // 创建PageBean对象
        PageBean<Borrow> pageBean = new PageBean<>();
        // 开启分页查询 PageHelp
        PageHelper.startPage(pageNo, pageSize);
        // 调用mapper
        PageInfo<Borrow> pageInfo = new PageInfo<>(borrowMapper.records(userId, state, searchKeyword));
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setList(pageInfo.getList());
        return pageBean;
    }

    /**
     * 获取某图书所有的借阅记录
     * @param bookId 图书ID
     * @param state 借阅状态
     */
    @Override
    public List<Borrow> getAllBorrowByBookId(Long bookId, Enum<BorrowState> state) {
        return borrowMapper.getAllBorrowByBookId(bookId, state);
    }

    /**
     * 归还图书
     * @param bookId 图书ID
     * @param state 记录状态
     */
    @Override
    public String repaid(Long bookId, BorrowState state) {
        // 获取当前用户ID
        Long userId = userService.getUserId();
        // 获取图书
        Book book = bookMapper.findById(bookId);
        // 获取借阅记录
        Borrow borrow = this.getBorrowByBookId(bookId, state);

        // 未领取状态下，直接完成归还
        if (borrow.getState() == BorrowState.UNCLAIMED) {
            // 如果图书为已借出状态，更新为可借阅
            if (book.getState() == BookState.BORROWED) {
                bookStateService.changeState(borrow.getBookId(), BookState.BORROWED, userId, "未领归还");
            }
            // 更新图书库存信息
            book.setStockCount(book.getStockCount() + 1);
            bookMapper.update(book);
            borrow.setState(BorrowState.REPAID);
            borrowMapper.repaid(borrow);
            return "归还成功";
        }
        borrow.setState(BorrowState.UNREPAID);
        borrow.setEndTime(LocalDateTime.now());
        borrowMapper.repaid(borrow);
        return "请将图书归还管理员";
    }

    /**
     * 删除借阅记录，未归还图书无法删除记录
     * @param id 借阅ID
     */
    @Override
    public void delete(Long id) {
        // 获取当前用户ID
        Long userId = userService.getUserId();
        // 获取借阅记录
        Borrow borrow = this.getBorrowById(id);
        // 未归还图书不允许删除记录
        if (borrow.getState() == BorrowState.UNCLAIMED || borrow.getState() == BorrowState.CLAIMED || borrow.getState() == BorrowState.UNREPAID) {
            throw new BusinessException("请先归还图书再进行操作");
        }
        // 获取图书
        Book book = bookMapper.findById(borrow.getBookId());
        book.setStockCount(book.getStockCount() + 1);
        // 更新图书库存信息
        bookMapper.update(book);
        // 如果图书为已借出状态，更新为可借阅
        if (book.getState() == BookState.BORROWED) {
            bookStateService.changeState(borrow.getBookId(), BookState.BORROWED, userId, "未领取归还库存");
        }
        borrowMapper.delete(borrow);
    }
}
