package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.entity.BookStateLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookStateLogRepository extends JpaRepository<BookStateLog, Long> {
    // 可自定义查询方法（如按图书ID查询日志）
    List<BookStateLog> findByBookId(Long bookId);
}
