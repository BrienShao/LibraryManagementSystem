package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.BookState;
import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.BookStateLog;
import com.example.librarymanagementsystem.exception.BusinessException;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.BookStateLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookStateService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStateLogRepository logRepository;

    public void changeState(Long bookId, BookState newState, Long operatorId, String notes) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessException("图书不存在"));

        BookState oldState = book.getState();
        if (!oldState.canTransitionTo(newState)) {
            throw new IllegalStateException("状态转换非法: " + oldState + " -> " + newState);
        }

        /*// 更新图书状态
        book.setState(newState);
        if (newState == BookState.BORROWED) {
            book.setDueDate(LocalDateTime.now().plusWeeks(2)); // 借期两周
        } else if (newState == BookState.AVAILABLE) {
            book.setCurrentBorrowerId(null);
            book.setDueDate(null);
        }*/

        // 保存状态变更日志
        BookStateLog log = new BookStateLog();
        log.setBookId(bookId);
        log.setOldState(oldState.name());
        log.setNewState(newState.name());
        log.setOperatorId(operatorId);
        log.setNotes(notes);
        logRepository.save(log);

        bookRepository.save(book); // 乐观锁自动校验版本
    }
}
