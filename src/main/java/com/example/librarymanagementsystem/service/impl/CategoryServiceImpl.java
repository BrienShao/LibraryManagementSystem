package com.example.librarymanagementsystem.service.impl;

import com.example.librarymanagementsystem.entity.Category;
import com.example.librarymanagementsystem.exception.BusinessException;
import com.example.librarymanagementsystem.mapper.CategoryMapper;
import com.example.librarymanagementsystem.service.CategoryService;
import com.example.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserService userService;

    @Override
    public void create(Category category) {
        // 判断是否存在同名分类
        Category oldCategory = categoryMapper.findByName(category.getCategoryName());
        if (oldCategory != null) {
            throw new BusinessException("存在同名分类");
        }
        category.setCreateUserId(userService.getUserId());
        categoryMapper.create(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.getAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void delete(Long id) {
        categoryMapper.delete(id);
    }

    @Override
    public void update(Category category) {
        // 判断是否存在同名分类
        Category oldCategory = categoryMapper.findByName(category.getCategoryName());
        if (oldCategory != null) {
            throw new BusinessException("存在同名分类");
        }
        categoryMapper.update(category);
    }
}
