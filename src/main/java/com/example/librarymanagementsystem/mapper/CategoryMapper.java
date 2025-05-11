package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // 根据分类名查找分类
    @Select("select * from category where category_name = #{categoryName}")
    Category findByName(String categoryName);

    @Insert("insert into category(category_name, category_alias, create_user_id, create_time, update_time) " +
            "VALUES (#{categoryName}, #{categoryAlias}, #{createUserId}, now(), now())")
    void create(Category category);

    @Select("select * from category")
    List<Category> getAll();

    @Update("update category set " +
            "category_name = #{categoryName}, " +
            "category_alias = #{categoryAlias}, " +
            "update_time = now() where id = #{id}")
    void update(Category category);

    @Select("select * from category where id = #{id}")
    Category findById(Long id);

    @Delete("delete from category where id = #{id}")
    void delete(Long id);
}
