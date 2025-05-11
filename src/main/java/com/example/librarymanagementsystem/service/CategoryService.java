package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Category;

import java.util.List;

public interface CategoryService {

    // 创建分类
    void create(Category category);

    // 修改分类
    void update(Category category);

    // 获取列表
    List<Category> getAll();

    // 获取分类信息
    Category findById(Long id);

    // 删除分类
    void delete(Long id);
}
