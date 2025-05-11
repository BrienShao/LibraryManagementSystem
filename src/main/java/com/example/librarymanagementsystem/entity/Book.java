package com.example.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;    // 图书名称
    @NotNull
    private String author;  // 图书作者
    @NotNull
    private String isbn;    // 图书ISBN编码
    @NotNull
    private String description; // 图书描述
    @NotNull
    private Integer pageCount;  // 图书页数
    @NotNull
    @URL
    private String image;   // 图书封面
    @NotNull
    private Double price;   // 单价
    @NotNull
    private String publisher;   // 出版商
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishedDate;   // 出版日期
    @NotNull
    private Integer stockCount; // 库存
    @NotNull
    private Long categoryId; // 分类ID
    private Long createUserId;   // 创建者ID
    private Long updateUserId;   // 更新者ID
    @Enumerated(EnumType.STRING)
    private BookState state = BookState.AVAILABLE; // 图书状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;    // 入库时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;    // 更新时间
}
