package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.entity.Category;
import com.example.librarymanagementsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 创建分类
     * @param category 分类信息
     */
    @PostMapping
    public void create(@RequestBody @Validated Category category) {
        categoryService.create(category);
    }

    /**
     * 分类列表
     */
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    /**
     * 获取分类信息
     * @param id 分类ID
     */
    @GetMapping("/detail")
    public Category detail(Long id) {
        return categoryService.findById(id);
    }

    /**
     * 修改分类
     * @param category 分类信息
     */
    @PutMapping
    public void update(@RequestBody @Validated Category category) {
        categoryService.update(category);
    }

    /**
     * 删除分类
     * @param id 分类ID
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
