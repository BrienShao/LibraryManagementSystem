package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.BookState;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Insert("insert into book (name, author, isbn, description, page_count, " +
            "image, price, publisher, published_date, stock_count, category_id, " +
            "create_user_id, update_user_id, state, create_time, update_time) " +
            "VALUES (#{name}, #{author}, #{isbn}, #{description}, #{pageCount}, " +
            "#{image}, #{price}, #{publisher}, #{publishedDate}, #{stockCount}, " +
            "#{categoryId}, #{createUserId}, #{updateUserId}, #{state}, now(), now())")
    void addBook(Book book);

    @Select("select * from book where id = #{bookId}")
    Book findById(Long bookId);


    List<? extends Book> list(Integer categoryId, BookState state, String searchKeyword);

    @Update("update book set name = #{name}, author = #{author}, isbn = #{isbn}, " +
            "description = #{description}, page_count = #{pageCount}, image = #{image}, name = #{name}, " +
            "price = #{price}, publisher = #{publisher}, published_date = #{publishedDate}, " +
            "stock_count = #{stockCount}, category_id = #{categoryId}, update_user_id = #{createUserId}, " +
            "state = #{state}, update_time = #{updateTime} where id = #{id}")
    void update(Book book);

    @Delete("delete from book where id = #{id}")
    void delete(Long id);
}
