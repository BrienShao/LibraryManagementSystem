package com.example.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private Long id;
    @NotNull
    private String categoryName;    // 分类名称
    @NotNull
    private String categoryAlias;   // 分类别名
    private Long createUserId;   // 创建者ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;    // 更新时间
}
